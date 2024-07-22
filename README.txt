JustDoIt - aplikace pro správu úkolů.
Autor: Dmitrii Egorov, 2024

=== Uživatelská dokumentace ===

1. ÚVOD

Aplikace JustDoIt je jednoduchá aplikace pro správu úkolů. Umožňuje uživatelům vytvářet, upravovat a
mazat seznamy s úkoly a úkoly samotné.

Každý uživatel má svůj vlastní účet, který může být chráněn heslem. Uživatelé se mohou přihlásit, odhlásit a
registrovat. Po přihlášení se uživatelé dostanou na hlavní stránku, kde vidí seznamy s úkoly, které si
vytvořili. Uživatelé mohou vytvářet nové seznamy, upravovat a mazat existující seznamy a přidávat do
seznamů nové úkoly, upravovat a mazat existující úkoly.

Seznamy a jejich úkoly jsou synchoronizovány s databází, takže uživatelé mohou pracovat s aplikací na
libovolném zařízení, na kterém je aplikace nainstalována.

Každý seznam má název a ikonu. Seznamy jsou zobrazeny podle abecedy.

Každý úkol má název, popis, termín splnění a prioritu. Uživatelé mohou úkoly třídit podle termínu
splnění, priority nebo názvu. Mimo jiné aplikace umožňuje uživatelům vyhledávat úkoly podle názvu
napříč všemi seznamy.

Na obrazovce je mimo jiné seznam "Dnes", ve kterém jsou zobrazeny nedokončené úkoly, které mají
termín splnění dnes anebo v minulosti. Každý den aplikace automaticky posílá oznamovací zprávu o
úkolech, které je třeba dnes splnit.

2. INSTALACE

Aplikace JustDoIt je mobilní aplikace pro OS Android verze 10 a vyšší. Aplikaci lze nainstalovat z
APK souboru, který je třeba stáhnout na zařízení a spustit. Po spuštění se zobrazí průvodce instalací,
který uživatele provede instalací aplikace.

Pro vytvoření APK souboru je třeba mít nainstalované vývojové prostředí Android Studio a mít zdrojové
kódy aplikace. Pro vytvoření APK souboru je otevřit zdrojové kódy jako projekt v Android Studiu a
spustit proces sestavení APK souboru: Build -> Build Bundle(s) / APK(s) -> Build APK(s).

3. POUŽITÍ

Poznámka: pro použití aplikace je občas nutné mít přístup k internetu.

3.1 Přihlášení a registrace

Po otevření aplikace se uživatel automaticky anonymně přihlásí a dostaňe se na hlavní obrazovku, kde
vidí seznamy s úkoly. Pro přihlášení ("Sign in") nebo registraci ("Sign up") je třeba kliknout na
tlačítko v pravém horním rohu obrazovky (ikonka s postavičkou).

Pro přihlášení je třeba zadat e-mail a heslo. Pokud uživatel nemá účet, může se zaregistrovat. Pro
registraci je třeba zadat e-mail, heslo a zopakovat heslo. Po registraci se uživatel automaticky přihlásí.

V případě ztráty hesla je možné ho obnovit pomocí tlačítka "Send recovery email". Uživatel zadá e-mail a
po stisknutí tlačítka obdrží na e-mail odkaz na webovou stránku pro obnovení hesla, kde může zadat
nové heslo.

3.2 Odhlášení a mazání účtu

Pro odhlášení je třeba kliknout na tlačítko v pravém horním rohu obrazovky (ikonka s postavičkou) a
vybrat možnost "Sign out". Po potvrzení odhlášení se uživatel automaticky odhlásí.

Pro smazání účtu je třeba kliknout na tlačítko v pravém horním rohu obrazovky (ikonka s postavičkou) a
vybrat možnost "Delete account". Po potvrzení smazání účtu se všechna data uživatele smažou a uživatel
bude automaticky přihlášen anonymně s novým účtem.

3.3 Správa seznamů

Pro vytvoření nového seznamu je třeba kliknout na tlačítko "+ Add list" v pravém dolním rohu obrazovky.
Zobrazí se nová obrazovka, kde je třeba zadat název seznamu a vybrat ikonu. Po zadání názvu a výběru ikony
je třeba kliknout na tlačítko "Done" v pravém dolním rohu obrazovky. Nový seznam se uloží a zobrazí se na
hlavní obrazovce.

Pro úpravu seznamu je třeba rozkliknout tlačítko editace seznamu (ikona tužky) napravo od názvu seznamu.
Zobrazí se stejná obrazovka jako v případě editace, kde je možné změnit zadané údaje. Po provedení
změn je třeba kliknout na tlačítko "Done" v pravém dolním rohu obrazovky. Změny se uloží a zobrazí se
na hlavní brazovce.

V případě mazání seznamu je třeba rozkliknout tlačítko s ikonou koše v pravém horním
rohu obrazovky. Po potvrzení smazání seznamu se seznam smaže a zmizí z hlavní obrazovky.

3.4 Správa úkolů

Pro vytvoření nového úkolu je nejprve třeba na hlavní obrazovce rozkliknout seznam, do kterého chcete
přidat úkol. Poté je třeba kliknout na tlačítko "+ Add task" v pravém dolním rohu obrazovky. Zobrazí se
nová obrazovka, kde je třeba zadat název úkolu, a také nepovinně popis úkolu, termín splnění a prioritu.
Po zadání údajů je třeba kliknout na tlačítko "Done" v pravém dolním rohu obrazovky. Nový úkol se uloží a
zobrazí se na obrazovce seznamu s úkoly.

Pro úpravu úkolu je třeba rozkliknout tlačítko editace úkolu (ikona tužky) napravo od názvu úkolu. Zobrazí
se stejná obrazovka jako v případě vytvoření, kde je možné změnit údaje úkolu. Po provedení změn je
třeba kliknout na tlačítko "Done" v pravém dolním rohu obrazovky. Změny se uloží a zobrazí se na
obrazovce.

V případě mazání úkolu je třeba rozkliknout tlačítko s ikonou koše v pravém horním rohu obrazovky. Po
smazání úkol zmizí.

Na obrazovce je možné třídit úkoly podle termínu splnění, priority nebo názvu. Pro třídění je třeba
kliknout na tlačítko s ikonou třídění v pravém horním rohu obrazovky a vybrat požadovanou možnost.

Pro označení úkolu jako splněného je třeba kliknout na tlačítko s ikonou čtvrce vlevo od názvu úkolu.
Po označení úkol se přesune na konec seznamu.

U každého úkolu je vidět zadané údaje včetně priority, které se zobrazuje jako několik vykřičníků podle
prioritní úrovně. Čím více vykřičníků, tím vyšší priorita.

3.5 Vyhledávání úkolů

Pro vyhledávání úkolů je třeba kliknout na tlačítko s ikonou lupy v pravém horním rohu hlavní obrazovky.
Zobrazí se obrazovka s polem pro zadání hledaného textu. Po zadání textu se zobrazí seznam úkolů,
které obsahují hledaný text v názvu (pozor, hledání je case-sensitive). Pro zrušení hledání je třeba
kliknout na tlačítko s ikonou kříže v pravém rohu pole pro hledání.

Na této obrazovce je možné třídit výsledky hledání podle termínu splnění, priority nebo názvu, a to
stejným způsobem jako na obrazovce seznamu s úkoly.

3.6 Seznam "Dnes"

Na hlavní obrazovce je zobrazen seznam "Dnes", ve kterém jsou zobrazeny nedokončené úkoly, které mají
termín splnění dnes anebo v minulosti. Úkoly jsou zobrazeny podle termínu splnění, ale uživatelé mohou
třídit stejně jako na obrazovce seznamu s úkoly.

Po označení úkolu jako splněného se úkol automaticky odstraní ze seznamu "Dnes".

3.7 Oznamovací zprávy

Každý den kolem deváté hodiny ráno aplikace automaticky posílá oznamovací zprávu o úkolech, které je
třeba dnes splnit. Zpráva sezobrazí jako notifikace na obrazovce zařízení. Uživatelé mohou rozkliknout
notifikaci a dostanou se na obrazovku seznamu "Dnes".

4. TECHNICKÁ POZNÁMKA

Aplikace JustDoIt je napsána v jazyce Kotlin pro OS Android s použitím knihovny Jetpack Compose.
Podporuje Material Design 3 a Dynamic Colors. Aplikace používá databázi Firestore pro uchovávání dat
uživatelů a Firebase Authentication pro autentizaci.
Aplikace používá WorkManager a NotificationManager pro odesílání oznamovacích zpráv. Pro logování
se používá Firebase Crashlytics. Hojně se využívají Flow a State z Kotlinu a Jetpack Compose pro
reaktivní aktualizaci UI.

