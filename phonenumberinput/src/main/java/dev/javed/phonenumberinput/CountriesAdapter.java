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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;


public class CountriesAdapter extends ArrayAdapter<Country> implements SpinnerAdapter {
    LayoutInflater layoutInflater;

    public CountriesAdapter(@NonNull Context context, List<Country> countries) {
        super(context, R.layout.item_country_code, R.id.name, countries);
        layoutInflater = LayoutInflater.from(getContext());
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        Country country = getItem(position);
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.spinner_country_code, parent, false);
        }
        ImageView imageView = convertView.findViewById(R.id.flag);
        TextView textView = convertView.findViewById(R.id.dial_code);
        assert country != null;
        textView.setText(String.format("+%s", country.getPhoneCode()));
        imageView.setImageResource(country.getResId(getContext()));
        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    private View getCustomView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_country_code, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.mName = convertView.findViewById(R.id.name);
            viewHolder.mDialCode = convertView.findViewById(R.id.dial_code);
            viewHolder.mFlag = convertView.findViewById(R.id.flag);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Country country = getItem(position);
        assert country != null;
        viewHolder.mFlag.setImageResource(country.getResId(getContext()));
        viewHolder.mName.setText(country.getCountryName());
        viewHolder.mDialCode.setText(String.format("+%s", country.getPhoneCode()));
        return convertView;

    }

    private static class ViewHolder {
        TextView mName;
        TextView mDialCode;
        ImageView mFlag;
    }
}
