import sqlite3
import json


def arrToString(arr):
	s = ''
	for col in arr:
		s += ''' '%s', ''' % col
	return s[:-2]

def createTable(cursor, tableName, cols):
	sql = 'create table %s (%s)' % (tableName, cols)
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


def dropTables(cursor):
	dropTable(cursor, 'users')
	dropTable(cursor, 'roles')


def createTables(cursor):
	createTable(cursor, 'users', 'id int NOT NULL UNIQUE, login text NOT NULL UNIQUE,\
		passw_hash text, role_id int')
	createTable(cursor, 'roles', 'id int NOT NULL UNIQUE, role text')
	fillWithTestData(cursor)



def main():
	conn  = sqlite3.connect('lbg.db')
	cursor = conn.cursor()
	dropTables(cursor)
	createTables(cursor)
	conn.commit()
	

if __name__ == '__main__':
	main()
