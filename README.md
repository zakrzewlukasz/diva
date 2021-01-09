# diva
Spring app 

Aplikacja do obsługi sytemu do archiwizacji danych opartych na kasetkach LTO. System nazywa się DIVArchive i wystawia API w javie, z kótrego korzystam w swoim projekcie.

Główne załoażenia:
- Archiwizacja
- Wyszukiwanie 
- Odzyskiwnaie 
- Infomacja o obenych reqestach, kótre przetwarza system 

Aby uzyskać prawidłowy widok aplikacji na stronie trzeba wykonać w terminalu polecenie "mvnw wro4j:run". Jest to co najmniej dziwne, ale ten thymeleaf powduje coś takiego. 
Nie upieram się przy nim i myślę, że lepiej jest wykonać widoki np. css.

Cały back siedzi w folderze api. Apka pod butonami wywoływać określone funcje z tego pliku.

Jak widać po zakładkach apka ma klika zadań, kótre wyszególniłem poniżej.

1. Archiwizacja obiektów w systemie. Wywołanie fukcji getArchiveRequest() z DivaHandler.java. Zebranie informacji od użytkownika, utworzenie obiektu ObjectRq.java oraz 
przetrzymanie requesta w bazie danych.
Problemem, który napotkałem w tym miejscu to połączenie widoku z zakładki Wyszukaj z widokiem z zakładki archiwizuj. Innymy słowy chciałbym, aby przy polu lista plików był 
przycisk, dzięki kótremu bedziemy mogli dodać pliki i do tego ma posłużyć ten widok z zakładki wyszukaj. Nie mogłem połaczyć tech dwóch rzeczy.... Sprawa jest o tyle złożona, że
będziemy musieli przeszukiwać serwer w poszukiwaniu plików, a  nie tak jak zawyczaj hosta. Napisałem fukcję w javie, kótra zwraca w postaci listy pliki/katalogi z danej ścieżki 
na serwerze. To info trzeba wrzucić do tej wyszukiwarki plików. Klasa do przeglądania plików na serwie znajduje się w katalogu file.

2. ...



Z góry sory za syf w projekcie i stertę niepotrzebnych cotrolerów i widoków, ale na ich podstawie próbowałem coś wykminić pod siebie.
