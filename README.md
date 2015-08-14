# NeetPicker
<img src="https://github.com/azril0409/NeetPicker/blob/master/demo.png?raw=true" alt="demo.png" width="300px">
#Compatibilty
API 11+
#Usage
gradle:
```
<dependency>
        compile 'com.neetoffice.library.neetpicker:neetpicker:1.0.2@aar'
</dependency>
```

in layout xml
```
<com.neetoffice.library.neetpicker.PickerView
        android:id="@+id/pickerView"
        app:neet_pickerview_linewidth="2dp"
        app:neet_pickerview_texts="@array/texts"
        app:neet_pickerview_textsize="24sp"
        app:neet_pickerview_maincolor="#0288ce"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />
        
```
#License
```
Copyright 2015 TU TSUNG-TSE
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
