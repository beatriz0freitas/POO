# **POO**

O projeto SpotifUM consiste no desenvolvimento de uma aplicação de gestão e reprodução de músicas, inspirada em plataformas de streaming como o Spotify. A aplicação permite a criação e organização de
músicas, álbuns, playlists e utilizadores com diferentes tipos de subscrição.
Entre as funcionalidades implementadas destacam-se a reprodução de músicas, a criação de playlists com base nas preferências dos utilizadores, o sistema de pontuação e a recolha de estatísticas de utilização.
A aplicação inclui ainda mecanismos de persistência para guardar e carregar o estado do sistema, garantindo que os dados dos utilizadores e das músicas se mantêm entre sessões.

comando para testar: mvn test

comando para compilar: mvn compile

comando para executar: ./run.sh

documentação: javadoc -d docs -sourcepath src/main/java -subpackages model:app:controller:view
