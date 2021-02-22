é um exemplo simular app do Netflix, usando conceito de recycler view de forma grid, placeholder para carregar imagem. 

permissão de internet no manifest.xml
```
<uses-permission android:name="android.permission.INTERNET" />
```


aplica o filtro como camada para imagem capa.
```
<layer-list xmlns:android="http://schemas.android.com/apk/res/android">
    <item
        android:id="@+id/cover_drawable"
        android:drawable="@drawable/placeholder_bg" />

    <item android:id="@+id/gradiente_drawable">
        <shape >
            <gradient
                android:angle="90"
                android:endColor="#FF111111"
                android:centerColor="#00000000"
                android:startColor="#FF111111"
                />
        </shape>
    </item>
</layer-list>
</layer-list>
```

### Autor
# Kao Yu Chun
* Developer Android / Back-End
* [LinkedIn](https://www.linkedin.com/in/kao-yu-chun-9b35a949/)

