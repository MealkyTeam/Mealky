# Mealky

Aplikacja książka kucharska.

Role:
Użytkownik

Technologie:
Kotlin, Java, Android, jUnit, dagger2, rxjava, retrofi, picasso.

Narzędzia:
Trello, Github, Android Studio, Slack.

Diagram przypadków użycia:

![mealky-przypadki uzycia](https://user-images.githubusercontent.com/43780500/46821807-0f428a00-cd8a-11e8-860e-589348a5d668.jpg)

Diagram ERD:
![mealky-diagram erd](https://user-images.githubusercontent.com/43789592/46903332-a2d3a200-ced3-11e8-829f-f1a0552c1523.png)
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

Przykładowe ikony kategorii ze strony "Moje Wypieki":
![tes2596259](https://user-images.githubusercontent.com/43780500/46664715-174fcd80-cbc2-11e8-8874-f5d8d79d617b.png)

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

Dzięki Marcin Kitowicz (github.com/kitek) za rady i sugestie odnośnie projektu.
