# Mealky

Aplikacja książka kucharska.

Role:
Użytkownik, Admin

Technologie:
Kotlin, Java, Android, jUnit, dagger2, rxjava, retrofi, picasso.

Narzędzia:
Trello, Github, Android Studio, Slack, Figma, Canva

Diagram przypadków użycia:

![mealky-przypadki uzycia](https://user-images.githubusercontent.com/43780500/46821807-0f428a00-cd8a-11e8-860e-589348a5d668.jpg)

Diagram ERD:
![erd](https://user-images.githubusercontent.com/43789592/48032693-efb83a80-e158-11e8-871c-f9c22b61ce40.png)
Spis funkcjonalności:
- Dodawanie przepisów;
- Modyfikowanie przepisów;
- Przepisy przyporządkowane do konkretnego konta;
- Przepisy na serwerze/chmurze;
- Wyszukiwanie przepisów po nazwie/składnikach/kategoriach;
- Zdjęcia główne;
- Druk przepisu bez zdjęcia;
- Do przemyślenia: aplikacja webowa;
- Dostęp do początkowej podstawowej bazy;
- Ulubione;
- Przepisy offline (początkowa baza przepisów);
- Funkcja przeglądania przepisów "kulinarny tinder";
- Przelicznik jednostek;
- Notatki do przepisów;
- Lista zakupów;
- Strona dla developera do akceptacji przepisów.

Przypadki "wrażliwe":
1. Szukanie: po składnikach, po kategoriach, po nazwach przepisu, po użytkowniku
2. Sortowanie: losowo, alfabetycznie, ilości ulubionych
3. Opcja zaskocz mnie: 
   Otwieramy losowy przepis (główne zdjęcie, nazwa, ilość ulubień)
    - swipe z prawej do lewej -> odrzucamy przepis -> wyświetl kolejny przepis
    - swipe z lewej do prawej -> otwieramy przepis
4. Strona przeznaczona do akceptacji nowy dodanych przepisów:
   Wyświetlają się nowe przepisy dodane przez użytkowników w aplikacji.
   Administrator ma możliwość zatwierdzenia lub odrzucenia przepisu:
   - zatwierdzenie zmienia status przepisu na zaakceptowany
   - odrzucenie wysyła komunikat do użytkownika z informacją zwrotną od administratora

-------------------------------------------------------------------
Lista zmian:

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
