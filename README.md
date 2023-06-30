# SWEproject2023

Progetto di ingegneria del software 2023.

Autori: Antonio Marusic, Filippo Garofalo, Andrea Lazzaretto, Kalana Kalupahana.
![My_Shelfie_box_ITA-ENG](https://user-images.githubusercontent.com/125985963/225013658-63a3e78f-3a04-4e8c-925b-2873ff4c31c5.png)
<p>L&apos;obbiettivo del progetto era quella di implementare il gioco da tavola My Shelfie, seguendo il pattern Model-View-Controller. L&apos;elaborato finale implementa la possibilit&agrave; di: &nbsp;&nbsp;</p>
<ul>
    <li>comunicare tra client-server sia seguendo il paradigma basato sui Socket sia quello basato su RMI; &nbsp;&nbsp;</li>
    <li>d&apos;interfacciarsi al gioco sia tramite la CLI che con la GUI, implementata con l&apos;architettura di javafx; &nbsp;&nbsp;</li>
    <li>realizzare il server in modo che possa gestire pi&ugrave; partite contemporaneamente; &nbsp;&nbsp;</li>
    <li>fare in modo che il server salvi periodicamente lo stato della partita su disco, in modo che l&apos;esecuzione possa riprendere da dove si &egrave; interrotta anche a seguito del crash del server stesso; &nbsp;</li>
    <li>I giocatori disconnessi a seguito della caduta della rete o del crash del client, possono ricollegarsi e continuare la partita. &nbsp;&nbsp;</li>
</ul>
<h1><span style="font-size: 30px;">Documentazione</span></h1>
<h2><span style="font-size: 28px;">UML</span></h2>
<p><span style="font-size: 16px;">I seguenti diagrammi rappresentano uno schema basico della relazione che intercorre tra le varie classi</span></p>
<ul>
    <li><span style="font-size: 17px;"><a href="https://github.com/FilippoGarofalo/ing-sw-2023-Garofalo-Lazzaretto-Kalupahana-Marusic/tree/main/derivables/UML">UML</a></span></li>
</ul>
<h2><span style="font-size: 28px;">Sequence Diagram</span></h2>
<p><span style="font-size: 16px;">I seguenti diagrammi mostrano uno schema semplificato del flusso di comunicazione tra le varie componenti del progetto nelle diverse fasi di gioco.</span></p>
<ul>
    <li><span style="font-size: 22px;"><a href="https://github.com/FilippoGarofalo/ing-sw-2023-Garofalo-Lazzaretto-Kalupahana-Marusic/tree/main/derivables/Sequence_diagrams">Sequence Diagram</a></span></li>
</ul>
<h2><span style="font-size: 28px;">JavaDoc</span></h2>
<p><span style="font-size: 16px;">La seguente documentazione offre un breve commento dei metodi pi&ugrave; complessi&nbsp;</span></p>
<ul>
    <li style="font-size: 22px;"><a href="https://github.com/FilippoGarofalo/ing-sw-2023-Garofalo-Lazzaretto-Kalupahana-Marusic/tree/main/javadoc">JavaDoc</a></li>
</ul>

<h2>Plug-in</h2>
<table style="width: 100%;">
    <tbody>
        <tr>
            <td style="width: 22.2654%;">Plug-in</td>
            <td style="width: 77.5491%;">Descrizione</td>
        </tr>
        <tr>
            <td style="width: 22.2654%;">maven</td>
            <td style="width: 77.5491%;">Strumento per la gestione della compilazione di progetti Java&nbsp;</td>
        </tr>
        <tr>
            <td style="width: 22.2654%;">junit</td>
            <td style="width: 77.5491%;">Framework utilizzato per fare testing</td>
        </tr>
        <tr>
            <td style="width: 22.2654%;">JavaFx</td>
            <td style="width: 77.5491%;">Libreria grafica di Java</td>
        </tr>
    </tbody>
</table>


<h2><span style="font-size: 30px;">Funzionalit&agrave;</span></h2>
<ul>
    <li><span style="font-size: 16px;">Regole complete</span></li>
    <li><span style="font-size: 16px;">CLI</span></li>
    <li><span style="font-size: 16px;">GUI</span></li>
    <li><span style="font-size: 16px;">Socket</span></li>
    <li><span style="font-size: 16px;">RMI</span></li>
    <li><span style="font-size: 16px;">Partite multiple</span></li>
    <li><span style="font-size: 16px;">Persistenza</span></li>
    <li><span style="font-size: 16px;">Resilienza alle disconnessioni</span></li>
</ul>

<h2><span style="font-size: 36px;">JAR</span></h2>
<p><span style="font-size: 16px;">I seguenti jar sono stati utilizzati per la consegna del progetto, permettono quindi il lancio del gioco secondo le funzionalit&agrave; descritte nell&apos;introduzione.</span></p>

<ul>
    <li style="font-size: 22px;"><a href="https://github.com/FilippoGarofalo/ing-sw-2023-Garofalo-Lazzaretto-Kalupahana-Marusic/tree/main/derivables/JAR">JAR</a></li>
</ul>

<p><span style="font-size: 30px;">come far partire il jar lato server:</span></p>
<code>java -jar your/path/to/jar/server.jar path/in/which/you/want/to/put/the/game/files serverIp</code>

<p><span style="font-size: 30px;">come far partire il jar lato client:</span></p>
<code>java -jar your/path/to/jar/client.jar serverIp</code>

<h2><span style="font-size: 30px;">Componenti del gruppo:</span></h2>
<ul>
    <li><span style="font-size: 18px;"><a href="https://github.com/FilippoGarofalo">Filippo Garofalo</a></span></li>
    <li><a href="https://github.com/andrealazzaretto">Andrea Lazzaretto</a></li>
    <li><a href="https://github.com/AnteMarusic">Antonio Marusic</a></li>
    <li><a href="https://github.com/kala1221">Kalana Kalupahana</a></li>
</ul>
<p><br></p>
