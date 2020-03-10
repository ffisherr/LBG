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

	insertIntoTable(cursor, 'users', [ '0', 'admin', '123', '0'])
	insertIntoTable(cursor, 'users', [ '1', 'student', '123', '1'])
	insertIntoTable(cursor, 'users', [ '2', 'user', '123', '2'])

	cursor.execute('insert into calendar values(?,?,?,?,?)', ('0', 'Event 1', 
		'2020-12-01 10:00', 'Test Event 1','{"tags":["tag1", "tag2"]}'))
	cursor.execute('insert into calendar values(?,?,?,?,?)', ('1', 'Event 2', 
		'2020-12-01 10:10', 'Test Event 2','{"tags":["tag3", "tag4"]}'))
	cursor.execute('insert into calendar values(?,?,?,?,?)', ('2', 'Event 3', 
		'2020-12-01 10:00', 'Test Event 3','{"tags":["tag3"]}'))


def dropTables(cursor):
	dropTable(cursor, 'users')
	dropTable(cursor, 'roles')
	dropTable(cursor, 'calendar')


def createTables(cursor):
	createTable(cursor, 'users', 'id int NOT NULL UNIQUE, login text NOT NULL UNIQUE,\
		passw_hash text, role_id int')
	createTable(cursor, 'roles', 'id int NOT NULL UNIQUE, role text')
	createTable(cursor, 'calendar', 'id int NOT NULL UNIQUE, title text, dt datetime, \
		about text, tags json1')
	fillWithTestData(cursor)



def main():
	conn  = sqlite3.connect('lbg.db')
	cursor = conn.cursor()
	dropTables(cursor)
	createTables(cursor)
	conn.commit()
	

if __name__ == '__main__':
	main()
