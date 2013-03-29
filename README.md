

                Tema1 IDP

                                Team Lambda
                                Gheorghiu Alexandru 342C3
                                Scarlat Georgiana 342C3



         Tema este disponibila la:
         ------------------------
            https://github.com/georgianascarlat/TeamLambda

         Mod de rulare:
         --------------
            ant
            ant run

         Simulare:
         --------

            Tema contrine doua simulari, una pentru tipul "buyer" si una pentru tipul "seller".
         Pentru a schimba tipul simularii se schimba in build,xml linia:
            <arg value="seller" />

            User-ul folosit va fi "ana", iar parola poate fi oricare.

            Fiecare din cele doua scenarii sunt implementate in NetworkImpl. Evenimentele aparute
         pe parcurs sunt fixe.
            Scenariu "buyer": utilizatorul va lansa cereri de oferta, iar la un moment dat vor fi lansate
         evenimente de tip useri noi, oferte facute de utilizatorii seller, etc. Acesta poate selecta din
         interfata sa accepte sau sa refuze o oferta. O licitatie se incheie atunci cand utilizatorul selecteaza
         "Accept Offer", "Refuze offer"sau cand renunta la cererea de oferta.
            Scenariul "seller": va putea sa faca oferte, iar apoi va primi raspuns de tip "Offer Accepted",
         "Offer Exceeded" sau "Offer Refuzed".  Atunci cand face oferte ii va aparea un dialog in care sa introduca
         pretul.

         Implementare:
         -------------

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
