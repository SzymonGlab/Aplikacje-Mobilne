# Aplikacje Mobilne

## Lista 1

#### Zadanie 1
Wykorzystując pliki z klasami i zasobami utwórz nowy projekt oraz przekopiuj je do odpowiednich katalogów i uruchom przykładową grę napisaną na pierwszym wykładzie.

#### Zadanie 2
Napisać dowolną aplikację w systemie Android z następującymi ograniczeniami:
* aplikacja zawiera co najmniej cztery widgety na ekranie co najmniej dwóch rodzajów np. TextView, EditView
* aplikacja powinna odpowiadać na co najmniej dwa zdarzenia np. dwa przyciski
* aplikacja powinna zmieniać dowolny aspekt wyglądu np. textview ma większe i grubsze czcionki
* aplikacja powinna dostosowywać się do wielkość ekranu (różne telefony i rozdzielczości)

## Lista 2

#### Zadanie 1
Napisz grę w kółko i krzyżyk, gdzie dwóch graczy na zmianę naciska przyciski w siatce 5x5 oznaczone odpowiednio przez "X" lub "O". Jeśli, któryś z graczy ułoży swoje litery pionowo, poziomo lub po przekątnej to wygrywa. Wykorzystaj odpowiedni Layout do rozłożenia przycisków oraz zrób tak aby przyciski wypełniały odpowiednio ekran i czcionka była odpowiednio duża (możesz to przetestować na emulatorze dla różnych rozdzielczości ekranów). Prostsza wersja, gry jest taka, że gracze są ludźmi i grają na zmianę. W bardziej zaawansowanej wersji zrób, aby można było grać z komputerem np. wymyślając jakąś strategie która będzie naśladowała zachowanie drugiego gracza.

#### Zadanie 2
Napisz aplikację "Wisielec", która wyświetla po kolei obrazek wisielca oraz słowo które gracz próbuje zgadnąć. Słowo wybierane jest losowo z dostępnego słownika. Oczywiście cały czas wyświetlane jest słowo z prawidłowo zgadniętymi literami np. dla słowa komputer jeśli gracz zgadł prawidłowo litery o, m i e, słowo będzie wyglądało mniej więcej tak *?om???e?*. Do utworzenia obrazków wykorzystaj np. program GIMP nazwij je odpowiednio wisielec0.png, wisielec1.png i tak dalej. Do wyświetlania obrazków wykorzystaj ImageView. Do przechowywania słownika wykorzystaj plik strings.xml.


## Lista 3

#### Zadanie 1
Napisz prostą aplikacje "To-Do", czyli listę zadań do zrobienia. Aplikacja zawiera ListView z potencjalną listą zadań. Na początku lista jest pusta, ale na dole aktywności znajduje się np. EditText oraz Button (Dodaj), gdzie użytkownik może wprowadzić opis nowego zadania. Dalej mamy możliwość wyboru ikonki zadania (obrazek) oraz czas wykonania zadania. Zaprojektuj odpowiedni layout pojedynczej pozycji ListView, który zawiera co najmniej obrazek, treść zadania oraz czas wykonania. Aplikacja powinna też mieć możliwość usuwania zadań z listy, przez dołączenie słuchacza do listy, który usuwa wybraną pozycję. Najlepiej wykorzystać do tego long click (metoda setOnItemLongClickListener). Zrób też tak, aby aplikacja pamiętała listę zadań po rotacji ekranu, czyli po zmianie z portrait na landscape i odwrotnie.

**Dodatkowe funkcje:**
* sortowanie zadań po czasie
* ustalenie priorytetów zadań
* sortowanie po ikonce (typ zadania)
* . . .

## Lista 4

#### Zadanie 1
Napisz aplikacje (galeria) przechowującą zdjęcia, każde zdjęcie dodatkowo zawiera krótki opis. Po uruchomieniu aplikacji, na początku pokazuje ona dostępne zdjęcia. Użytkownik może wybrać dowolną pozycję, aby zobaczyć większe zdjęcie i opis. Na ekranie dodatkowo, mamy możliwość ocenienia zdjęcia przez np. "gwiazdki". Aplikacja powinna natomiast obsługiwać:

* co najmniej dwie aktywności
* przekazywać informacje z jednej aktywności do drugiej wykorzystując intencje
* druga aktywność powinna wracać informacje do pierwszej o liczbie gwiazdek, po czym w pierwszej aktywności obrazki zostają odpowiednio posortowane po liczbie gwiazdek
* poprawnie obsługiwać cykl życia aktywności tzn. onCreate, onStart, onResume, onPause, onStop, onDestroy, ... (te które są potrzebne) 
* zapamiętywać swój stan po zmianie orientacji ekranu
* **posiadać możliwość robienia zdjęć (obsługi Camera)** i dodawania do kolekcji
