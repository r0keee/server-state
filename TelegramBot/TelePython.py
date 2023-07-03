from fileinput import filename
import telebot
import random
import string
import os
import mysql.connector

from telebot import types

ErrorReplies = ["Говори по-русски. Я тебя не понимаю", "Я не знаю язык Шрека", "Тебя изнасилуют в январе", "Про маму было лишнее", "Напоминаю, биться головой о клавиатуру - идея такая себе"]
PhotoReplies = ["Один вопрос: ты зачем это кидаешь боту?", "Мммммм...", "Вот вместо того, чтобы такое делать, мог бы делами важными заняться"]
VideoReplies = ["Тебе напомнить, что я бот и не умею смотреть видео?", "ММММММММММММММММ..."]

API_TOKEN = '5449616531:AAEccMd5SXjIYFzPtpWbzcwpD2_42xjThDQ'
bot = telebot.TeleBot(API_TOKEN)

print("Bot started")
print("Token: " + API_TOKEN)
print(" ")

@bot.message_handler(commands=['start'])
def start(message):
    mess = f'Привет, <b>{message.from_user.first_name}</b> \nВыбери из появившегося списка необходимую <b>услугу</b>, либо просто <i>напиши</i>/<i>скинь файл</i>, чтобы я понял, как тебе помочь 😁'

    markup = types.ReplyKeyboardMarkup(resize_keyboard=True)
    item1 = types.KeyboardButton('🚀 Состояние сервера')
    item2 = types.KeyboardButton('🤖 Сайт')

    markup.add(item1, item2)

    bot.send_message(message.chat.id, mess, parse_mode='html', reply_markup=markup)

@bot.message_handler(content_types=['text'])
def get_user_text(message):
    if message.chat.type == 'private':
        if message.text == '🚀 Состояние сервера':
            print("Used Item1")
            mydb = mysql.connector.connect(host="212.22.92.26", user="qemgkhrw", password="changeme", database="qemgkhrw_43131")
            mycursor = mydb.cursor()
            mycursor.execute("SELECT working_state FROM properties")
            myresult = mycursor.fetchone()
            print(myresult)
            if myresult[0]==0:
                bot.send_message(message.chat.id, "Сервер остановлен 🔴")
            if myresult[0]==1:
                bot.send_message(message.chat.id, "Сервер работает 🟢")
            if myresult[0]==2:
                bot.send_message(message.chat.id, "Сервер находится на тех. обслуживании 🟠")
            myresult = None
            mydb.close()
        elif message.text == '🤖 Сайт':
            markup = types.InlineKeyboardMarkup()
            markup.add(types.InlineKeyboardButton("Посетить веб сайт", url="https://rebirth-mc.xyz/state/"))
            bot.send_message(message.chat.id, "Держи ссылку на сайт", reply_markup=markup)
            print("Used Item2")
        else:
            bot.send_message(message.chat.id, random.choice(ErrorReplies))
            print("User " + message.from_user.first_name + " wrote: " + message.text)

@bot.message_handler(content_types=['photo'])
def get_user_photo(message):
    bot.send_message(message.chat.id, random.choice(PhotoReplies))
    photo_id = message.photo[-1].file_id
    file_photo = bot.get_file(photo_id)
    filename, file_extension = os.path.splitext(file_photo.file_path)

    downloaded_file_photo = bot.download_file(file_photo.file_path)

    src = 'photos/' + message.photo[-1].file_id + file_extension
    with open(src, 'wb') as new_file:
        new_file.write(downloaded_file_photo)
    print("User " + message.from_user.first_name + " send a photo. Succesfuly saved in images folder")

@bot.message_handler(content_types=['video'])
def get_user_video(message):
    bot.send_message(message.chat.id, random.choice(VideoReplies))
    print("User " + message.from_user.first_name + " send a video. Succesfuly saved in video folder")


bot.polling(none_stop=True)
