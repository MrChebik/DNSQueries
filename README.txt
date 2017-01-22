== RU ===== Заметки автора =============================================================================================

Цель программы:
Найти IP адрес, требуемого доменного имени, основываясь на корневом домен-сервере, сервере первого и второго уровня.

Аргументы:
Клиент принимает два аргумента: --server, --query
Сервер принимает аргумент, который обозначает DNS сервер домена. (Не удивляйтесь, что Server имеет аргумент. На самом
деле, ему он не нужен. Но, так как это вымышленные сервера, и запускаются с одного класса, то нужно хотя бы как-то их
различать, дабы они получили свои данные о доменах)

Была использована технология Java Reflection, чтобы реализовать присваивание значения аргументов, в любом порядке.

Порты:
Используемыe порты - 2048-2055, так как на Linux, порты с 0 по 1023 - системные, а 53 порт - держит демон 'named'.

UDP/TCP протоколы и запросы:
Реализована система 2-х протоколов на 1 порту. Тоесть, сервер может принимать/отсылать информацию с 1-го порта,
по 2-м протоколам.
Флаг TC - работает. Чтобы удостовериться, что это так, достаточно изменить размер буфоров до 8 байт, и в файле
'config_client.properties' удалить в запросе первые 4 буквы 'www.'
Клиент может отправлять любой запрос серверу. Главное, чтобы сервер имел ответ на него. Для этого, нужно создавать хотя
бы реальные IP адреса для них. Можете попробовать отправить запрос 'my.test.com'.

GUI:
JavaFX, перед запуском, основной поток останавливается на 2 секунды, чтобы дать загрузиться JavaFX интерфейсу.

Корректный запуск программы:
В среде разработки, настроить запуск классов Main. Добавив 4 настраеваемых запуска.
1 - В модуле Server, класс Main, аргумент -- 'none' (none -- Первичный DNS сервер клиента, которые работает рекурсивно)
2 - В модуле Server, класс Main, аргумент -- 'root' (root -- Корневой DNS сервер, который ищет, какой сервер знает,
что-либо о домене первого уровня (в нашем случае -- com))
3 - В модуле Server, класс Main, аргумент -- 'com' (com -- DNS сервер домена com, который ищет информацию о домейне
второго уровня (в нашем случае -- test.com))
4 - В модуле Server, класс Main, аргумент -- 'test.com' (test.com -- DNS сервер домена test.com, который ищет
информацию о домейне третьего (в нашем случае -- www.test.com))
5 - В модуле Client, класс Main, аргументы -- '--query www.test.com --server 127.0.0.1' (Вписывать аргументы не
обязательно, они могут быть получены с properties файлов)

== EN ===== Author's notes =============================================================================================

Program purpose:
To find the IP address, the required domain name, based on root the domain server, the server of the first and second
level.

Arguments:
The client accepts two arguments: --serer, --query
The server accepts an argument which designates the domain DNS server. (Be not surprised that Server has an argument.
On most business, it isn't necessary to it. But, as it is fictional servers, and are launched from one class, it is
necessary at least somehow them to distinguish that they obtained the data on domains)

The Java Reflection technology was used to realize assignment of value of arguments, in any order.

Ports:
Used ports - 2048-2055, as on Linux, ports from 0 to 1023 - system, and port 53rd - are kept by the demon of 'named'.

UDP/TCP protocols and requests:
The system of 2 protocols on 1 port is realized. That is, the server can accept/send information from the 1st port,
according to the 2nd protocols.
TC flag - works. To make sure that it so, is enough to change the size of buffer to 8 bytes, and in the file
'config_client.properties' in a request to remove the first 4 letters 'www'.
The client can send any request to the server. The main thing that the server had the response to it. For this purpose,
it is necessary to create at least real IP addresses for them. You can try to send a request of 'my.test.com'.

GUI:
JavaFX, before start, the main thread stops for 2 seconds to allow to boot JavaFX to the interface.

Incorrect start of the program:
In a development environment to set up start of the classes Main. Having added 4 set-up starts.
1 - In the Server module, the class Main, an argument - 'none' (none - Primary DNS server of the client which works
recursively)
2 - In the Server module, the class Main, an argument - 'root' (root - The Root DNS server which looks for what server
knows, something about the domain of the first level (in our case - com))
3 - In the Server module, the class Main, an argument - 'com' (com - The DNS server of the com domain which looks for
information on the domeena the second level (in our case - test.com))
4 - In the Server module, the class Main, an argument - 'test.com' (test.com - The DNS server of the test.com domain
which looks for information on the domeena of the third (in our case - www.test.com))
5 - In the Client module, the class Main, arguments - '--query www.test.com --server 127.0.0.1' (To inscribe arguments
not surely, they can be received with properties of files)

========================================================================================================================