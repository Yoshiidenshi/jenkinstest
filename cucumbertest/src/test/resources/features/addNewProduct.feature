## language: ru
#
#@all
#@add_new_product
#Функция: Добавление нового продукта в список товаров
#
#
#  Предыстория:
#
#    * Тестовый стенд запущен, открыта страница по адресу "http://localhost:8080/food"
#    * К стенду подключена БД
#    * Товара с названием "Авокадо" в таблице не существует
#
#
#  Сценарий: Добавление нового продукта
#    * Нажать кнопку "Добавить"
#    * В поле названия вводится значение "Авокадо"
#    * В поле типа продукта выбирается значение "Фрукт"
#    * Флаг экзотичности содержит значение "true"
#    * Нажать кнопку "Сохранить"
#    * Проверить существование товара "Авокадо" в БД
#    * Удаление товара "Авокадо" через запрос в БД
#    * Проверить отсутствие "Авокадо" через запрос в БД
#    * Закрыть браузер