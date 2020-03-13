import sqlite3
import json


def arrToString(arr):
	s = ''
	for col in arr:
		s += ''' '%s', ''' % col
	return s[:-2]


def createTable(cursor, tableName, cols):
	sql = 'create table if not exists %s (%s)' % (tableName, cols)
	print(sql)
	cursor.execute(sql)


def dropTable(cursor, tableName):
	sql = 'drop table %s' % tableName
	cursor.execute(sql)


def insertIntoTable(cursor, table, values):
	values = arrToString(values)
	print('insert into %s values(%s)'%(table, values))
	cursor.execute('insert into %s values(%s)'%(table, values))


def fillWithTestData(cursor):
	insertIntoTable(cursor, 'roles', ['0', 'admin'])
	insertIntoTable(cursor, 'roles', ['1', 'student'])
	insertIntoTable(cursor, 'roles', ['2', 'unknownUser'])

	insertIntoTable(cursor, 'universities', ['0', 'МГТУ им. Н.Э.Баумана'])
	insertIntoTable(cursor, 'universities', ['1', 'МГУ'])
	insertIntoTable(cursor, 'universities', ['2', 'МЭИ'])

	cursor.execute('insert into calendar values(?,?,?,?,?)', ('0', 'Event 1', 
		'2020-03-10 10:25', 'Test Event 1','{"tags":["student", "common"]}'))
	cursor.execute('insert into calendar values(?,?,?,?,?)', ('1', 'Event 2', 
		'2020-12-01 10:10', 'Test Event 2','{"tags":["tag3", "common"]}'))
	cursor.execute('insert into calendar values(?,?,?,?,?)', ('2', 'Event 3', 
		'2020-10-01 10:00', 'Test Event 3','{"tags":["common"]}'))


	insertIntoTable(cursor, 'users', [ '0', '0', '0', 'Андрей', 'Попов',
	'Николаевич', 'andreiAdmin', '123'])
	insertIntoTable(cursor, 'users', [ '1', '1', '1', 'Иван', 'Иванов',
	'Иванович', 'ivanStudent', '123'])
	insertIntoTable(cursor, 'users', [ '2', '1', '0', 'Петр', 'Петров',
	'Петрович', 'petrStudent', '123'])
"""
	insertIntoTable(cursor, 'messages', ['0', 'TestText0', '2020-01-11 10:00', '1'])
	insertIntoTable(cursor, 'messages', ['1', 'TestText1', '2020-01-11 10:01', '2'])
	insertIntoTable(cursor, 'messages', ['2', 'TestText2', '2020-01-11 10:02', '0'])"""




def dropTables(cursor):
	dropTable(cursor, 'users')
	dropTable(cursor, 'roles')
	dropTable(cursor, 'calendar')
	dropTable(cursor, 'messages')
	dropTable(cursor, 'universities')


def createTables(cursor):
	createTable(cursor, 'users', 'id int NOT NULL UNIQUE, role_id int, university_id int,\
		FirstName text, SurName text, SecondName text,login text NOT NULL UNIQUE,\
		passw_hash text')
	createTable(cursor, 'roles', 'id int NOT NULL UNIQUE, role text')
	createTable(cursor, 'calendar', 'id int NOT NULL UNIQUE, title text, dt datetime, \
		about text, tags json1')
	createTable(cursor, 'messages', 'id int NOT NULL UNIQUE, message_text text, dt datetime,\
		sender_id int, sender_login text')
	createTable(cursor, 'universities', 'id int not NULL UNIQUE, name text')
	fillWithTestData(cursor)



def main():
	conn  = sqlite3.connect('lbg.db')
	cursor = conn.cursor()
	dropTables(cursor)
	createTables(cursor)
	conn.commit()
	

if __name__ == '__main__':
	main()
