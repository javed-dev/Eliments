/*
 * Copyright (c) 2020.  Zenex.Tech@ https://zenex.tech
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under
 * the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.javed.phonenumberinput;

import android.content.Context;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import io.michaelrocks.libphonenumber.android.NumberParseException;
import io.michaelrocks.libphonenumber.android.PhoneNumberUtil;
import io.michaelrocks.libphonenumber.android.Phonenumber;

public class PhoneNumberInputText extends LinearLayout {

    private Spinner countryCodeSpinner;
    private EditText phoneNumberText;
    private Country country;

    private PhoneNumberUtil phoneNumberUtil;


    public PhoneNumberInputText(Context context) {
        this(context,null);
    }

    public PhoneNumberInputText(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PhoneNumberInputText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        phoneNumberUtil = PhoneNumberUtil.createInstance(context);
        inflate(getContext(), R.layout.phone_number_input, this);
        prepareView();
        countryCodeSpinner.setSelection(Countries.getCountryIndex("IN"), true);
//        getRootView().setBackgroundResource(R.drawable.border);
    }

    private void prepareView() {
        countryCodeSpinner = findViewById(R.id.country_code_spinner);
        phoneNumberText = findViewById(R.id.phone_number_text);
        if (phoneNumberText == null || countryCodeSpinner == null) {
            throw new IllegalStateException("Please provide a valid xml layout");
        }

        final CountriesAdapter adapter = new CountriesAdapter(getContext(), Countries.COUNTRIES);
        countryCodeSpinner.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard();
                return v.performClick();
            }
        });

        final TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String rawNumber = s.toString();
                if (rawNumber.isEmpty()) {
                    countryCodeSpinner.setSelection(Countries.getCountryIndex("IN"), true);
                } else {
                    if (rawNumber.startsWith("00")) {
                        rawNumber = rawNumber.replaceFirst("00", "+");
                        phoneNumberText.removeTextChangedListener(this);
                        phoneNumberText.setText(rawNumber);
                        phoneNumberText.addTextChangedListener(this);
                        phoneNumberText.setSelection(rawNumber.length());
                    }
                    try {
                        Phonenumber.PhoneNumber number = parsePhoneNumber(rawNumber);
                        if (country == null || Integer.parseInt(country.getPhoneCode()) != number.getCountryCode()) {
                            selectCountry(number.getCountryCode());
                        }
                    } catch (NumberParseException ignored) {
                    }
                }
            }
        };

        phoneNumberText.addTextChangedListener(textWatcher);

        countryCodeSpinner.setAdapter(adapter);
        countryCodeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                country = adapter.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                country = null;
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PhoneNumberInputText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private Phonenumber.PhoneNumber parsePhoneNumber(String number) throws NumberParseException {
        String defaultRegion = country != null ? country.getCountryCode().toUpperCase() : "";
        return phoneNumberUtil.parseAndKeepRawInput(number, defaultRegion);
    }

    /**
     * Gets phone number.
     *
     * @return the phone number
     */
    public String getPhoneNumber() {
        try {
            Phonenumber.PhoneNumber number = parsePhoneNumber(getRawInput());
            return phoneNumberUtil.format(number, PhoneNumberUtil.PhoneNumberFormat.E164);
        } catch (NumberParseException ignored) {
        }
        return getRawInput();
    }

    private String getRawInput() {
        return phoneNumberText.getText().toString();
    }

    /**
     * Sets default country.
     *
     * @param countryCode the country code
     */
    public void setDefaultCountry(String countryCode) {
        for (int i = 0; i < Countries.COUNTRIES.size(); i++) {
            Country country = Countries.COUNTRIES.get(i);
            if (country.getCountryCode().equalsIgnoreCase(countryCode)) {
                this.country = country;
                countryCodeSpinner.setSelection(i);
            }
        }
    }

    private void selectCountry(int dialCode) {
        for (int i = 0; i < Countries.COUNTRIES.size(); i++) {
            Country country = Countries.COUNTRIES.get(i);
            if (Integer.parseInt(country.getPhoneCode()) == dialCode) {
                this.country = country;
                countryCodeSpinner.setSelection(i);
            }
        }
    }

    /**
     * Sets phone number.
     *
     * @param rawNumber the raw number
     */
    public void setPhoneNumber(String rawNumber) {
        try {
            Phonenumber.PhoneNumber number = parsePhoneNumber(rawNumber);
            if (country == null || Integer.parseInt(country.getPhoneCode()) != number.getCountryCode()) {
                selectCountry(number.getCountryCode());
            }
            phoneNumberText.setText(phoneNumberUtil.format(number, PhoneNumberUtil.PhoneNumberFormat.NATIONAL));
        } catch (NumberParseException ignored) {
        }
    }

    private void hideKeyboard() {
        ((InputMethodManager) getContext().getSystemService(
                Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(phoneNumberText.getWindowToken(), 0);
    }


}
