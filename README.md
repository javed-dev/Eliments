# Eliments

[![Build status](https://build.appcenter.ms/v0.1/apps/15388753-e05a-4df8-8ff7-83d4a1f845e1/branches/master/badge)](https://appcenter.ms)
[ ![Download](https://api.bintray.com/packages/javed-hussain/maven/phonenumberinput/images/download.svg?version=0.0.1-beta01) ](https://bintray.com/javed-hussain/maven/phonenumberinput/0.0.1-beta01/link)
[![API](https://img.shields.io/badge/API-16%2B-orange.svg?style=flat)](https://android-arsenal.com/api?level=16)

Eliments is a UI toolkit for light-weight android UI components. Following components are available as a part of this project.

## PhoneNumberInputText
This is a lightweight phone number input edit-text with country codes and their flags. It has built in validation of phone numbers using android optimized port [libphonenumber-android](https://github.com/MichaelRocks/libphonenumber-android) of google's [libphonenumber](https://github.com/google/libphonenumber). this library is inspired by [android-phone-field](https://github.com/lamudi-gmbh/android-phone-field)  

<p float="left">
<img alt="Border Input Background" src = "https://github.com/javed-dev/Eliments/blob/master/border_input_background.jpg?raw=true" width="33%"/>
<img alt="Underline Input Background" src = "https://github.com/javed-dev/Eliments/blob/master/underline_input_background.jpg?raw=true" width="33%"/>
<img alt="Country Code List" src = "https://github.com/javed-dev/Eliments/blob/master/country_code_list.jpg?raw=true" width="33%"/>
</p>

The library has two built in backgrounds as displayed below along with their syntax - 
1. Border
2. Underline

## Features

- Displays the correct country flag if the user enters a valid international phone number
- Allows the user to choose the country manually and only enter a national phone number
- Allows you to choose a default country, which the field will change to automatically if the user chose a different country then cleared the field.
- Validates the phone number
- Returns the valid phone number including the country code


