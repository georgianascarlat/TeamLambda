

                Tema1,3 IDP

                                Team Lambda
                                Gheorghiu Alexandru 342C3
                                Scarlat Georgiana 342C3



         Tema este disponibila la:
         ------------------------
            Client:
            https://github.com/georgianascarlat/TeamLambda

            Web Service:

            https://github.com/georgianascarlat/LambdaService

         Mod de rulare:
         --------------
            ant
            ant run

            La logarea din interfata grafica, utilizatorul va trebui sa specifice user-ul si parola.
            De asemenea, va specifica si un fisier in care se afla wishlist-ul acestuia (serviciile
            pe care doreste sa le cumpere/vanda) si tipul acestuia. Implicit va aparea fisierul 'wishlist1.txt',
            dar acesta poate fi schimbat. Pentru a alege tipul utilizatorului, acesta trebuie specificat
            pe prima linie din fisier. Pe urmatoarele linii sunt specificate numele serviciilor.



         Implementare:
         -------------

         Tema1:
         ------
            Am folosit patternul Mediator pentru a media comunicarea intre cele 3 module.
            Am folosit patternul State pentru a face actiuni diferite la aparitia evenimentelor in functie de starea
         "buyer" sau "seller".
            Am folosit patternul Command pentru a procesa evenimentele aparute din interfata (butoane, menu item-uri),
          dar si pentru a procesa evenimentele venite de la celelalte module (prin intermediul mediatorului).
          Pentru a face modelele cu care am lucrat thread safe, modului de GUI porneste la initializare un worker
          care asteapta comenzi venite din exterior (mediator). Acest worker foloseste modelul producator-consumator,
          in sensul ca sunt submise comenzi, iar worker-ul asteapta ca acestea sa apara pentru a fi procesate (folosind
          publish-process).  Am folosit, de asemenea, SwingWorker si pentru actualizarea ProgessBar-ului.
            Am folosit de asemenea si pattern-ul Factory pentru a-mi genera instante de SessionState.

         Tema3:
         ------
            Persistenta:
            ------------
                Baza de date este initializata folosind scriptul 'sql/init.sql'.
                In acest script sunt creati utilizatorul, baza de date si tabelele necesare.
                Structura tabelelor este descrisa in diagrama 'diagram.jpg'.
                Pentru a implementa persisitenta datelor referitoare la useri si servicii am folosit
            Hibernate(JPA). Plecand de la tabelele create cu scriputl de initializare am generat POJO-uri.
                Pentru o mai buna separare a partii de procesarea a datelor din baza de date, am implementat
            un DAO(Data Access Object) generic, care ofera operatiile de baza de CRUD(Create Read Update Delete)
            plus alte operatii necesare(findAll, findByColumnName).
                De asemenea, am implementat un manager care realizeaza operatii la nivel de sesiune, folosindu-se
            de DAO-urile corespunzatoare entitatilor user si service. Aceste operatii sunt folosite pentru implementarea
            serviciilor web.

            Servicii Web:
            -------------
                Pentru partea de servicii web am folosit Axis si Tomcat. Serviciile web expuse sunt:
                - logoutUser  - schimba statusul user-ului in inactiv
                - loginUser - daca user-ul nu exista in BD, atunci il adauga, iar daca exista deja verifica daca
           parola si tipul sunt valide, iar apoi ii schimba status-ul in activ
                - relevantUsers  - obtine o lista cu utilizatorii care sunt de interes pentru un user cu un anumit tip
           si pentru un anumit serviciu (aceast serviciu este folosit de buyer-i atunci cand lanseaza o oferta, iar de
           seller-i atunci cand fac login)
                Pe baza serviciilor implementate am generat un fisier wsdl, disponibil la adresa:
           http://nogai.dlinkddns.com:8080/services/LambdaWebService?wsdl

           Web Service Client:
           -------------------
                Pentru a implementa clientul web service-urilor am generat automat client stub-urile din wsdl-ul
           mentionat anterior si am implemetat interfata WebServiceClient, in care am apelat efectiv serviciile.

           Observatii:
           -----------
                  Web service-ul ruleaza pe un calculator tot timpul, dar exista momente cand nu este accesibil
           deoarece sunt probleme cu DNS-ul uneori(trebuie sa folosesc nume deoarece RDS-ul schimba nu mentine
           ip-uri stabile).



