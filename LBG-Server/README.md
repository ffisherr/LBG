Для того, чтобы запустить на своем устройстве сервер нужно
1. Создать виртуальное окружение python. Необходим интерпретатор Python версии 3.5. Запустите в командной строке команду
python -m venv env
После этого создастся папка env, внутри которой будет лежать локальный интерпретатор и библиотеки. 
2. Запустить локальный интерпретатор
(Для unix) source env/bin/activate
(ля windows) ./env/bin/activate.sh
3. Установить необходимые библиотеки командой
pip install -r requirements.txt
4. Запустить сервис
python rest_service.py