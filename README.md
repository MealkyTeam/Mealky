# Mealky

Android application - cookbook

English version of readme: TO BE DONE

Aplikacja książka kucharska.

Role:
Użytkownik, Admin.

Technologie:
Kotlin, Java, Android, jUnit, dagger2, rxjava, retrofi, picasso.

Narzędzia:
Trello, Github, Android Studio, Slack, Figma, Canva.

Biblioteki:
- com.google.android.material  
- androidx.lifecycle  
- androidx.appcompat  
- com.mindorks:placeholderview  
- com.jakewharton.timber  
- com.squareup.okhttp3:logging-interceptor  
- androidx.room  
- io.reactivex.rxjava2
- com.squareup.picasso  
- com.squareup.retrofit2t  
- com.google.dagger:dagger  
- org.mockito  
- org.robolectric  


[Diagram ERD](documentation/diagram_ERD.png)  

[Diagram przypadków użycia](documentation/diagram_przypadków_użycia.jpg)  

[Planowany spis funkcjonalności](documentation/planowany_spis_funkcjonalności.txt)  

[Podział obowiązków](documentation/podział_obowiązków.txt)  

[Przypadki wrażliwe](documentation/przypadki_wrazliwe.txt)  


-------------------------------------------------------------------
Lista zmian:

Wersja 1.0.0
- Wdrożenie systemu Continuous integration (bitrise.io), który automatyzuje proces testowania i wydawania aplikacji do użytkowników.
- Wdrożenie systemu Crashlytics wraz z aplikacją Beta, aby w łatwy sposób można było mierzyć statystyki aplikacji i rozprowadzać aplikację po testerach.
- Dodano kilkadziesiąt testów jednostkowych do aplikacji, oraz kilka integracyjnych.
- Zaimplementowano szukanie w aplikacji wraz z mechanizmami debounce aby zminimalizować ilość requestów wysyłanych do API.
- Dodano możliwość resetowania hasła w aplikacji.
- Poprawiono błąd z brakiem możliwości scrollowania aplikacji na ekranie z rejestracją przy małych urządzeniach.
- Poprawiono inne pomniejsze błędy.

Wersja 0.3.0 
- Aktualizacja bibliotek projektu.
- Nowy pasek postępu na liście posiłków.
- Usunięto odświeżanie listy posiłków po przesunięciu w dół.
- Poprawiony stosunek obrazków w opcji "Odkryj posiłek".
- Poprawione zapisywanie pozycji na liście posiłku.
- Poprawiono inne małe błędy.
- Dodano dokumentację.
- Dodano ikonę aplikacji.
- Dodano składniki do posiłku i możliwość łatwego dodania ich do listy zakupów.
- Dodano listę zakupu, a na niej możliwość dodawania kolejnych składników, usuwania, edycji.
- Dodano informację o tym że trzeba potwierdzić mail przy rejestracji.
- Dodano wiele testów do aplikacji.

Wersja 0.2.0
- Odświeżanie listy posiłków.  
- Stronicowanie listy posiłków.  
- Przesuwanie listy posiłków w nieskończoność.  
- W pełni działająca opcja “Odkryj posiłek”.  
- Dodany górny pasek nawigacji z szukaniem.  
- Dodano zapisywanie stanu, w którym ekran został przekręcony.  
- Dodano sortowanie posiłków po dacie dodania.  
- Dodano wsparcie do kilku obrazków na jeden posiłek.  
- Dodano ekran powitalny.  
- Dodano rejestrację.  
- Rejestracja z potwierdzeniem maila.  
- Dodano logowanie.  
- Dodano autoryzację.  
- Pierwsza iteracja strony administratora skończona. (Logowanie, szablony CRUD-ów).  

Wersja 0.1.0  
- Prosta lista posiłków pobierana z internetu  
- Działający dolny pasek nawigacji   
- Puste ekrany listy posiłków, opcji “Odkryj posiłek”  

Dzięki Marcin Kitowicz (github.com/kitek) za rady i sugestie odnośnie projektu.
