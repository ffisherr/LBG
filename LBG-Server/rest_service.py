import sqlite3
import json
from model import User, Event, Message, University
from db import insertIntoTable
from flask import Flask, request
from flask_restful import Resource, Api
from flask_jsonpify import jsonify


app = Flask(__name__)
api = Api(app)


class UserAdd(Resource):
	def post(self):
		try:
			conn       = sqlite3.connect('lbg.db')
			cursor     = conn.cursor()
			Login      = request.json['login']
			Passw_hash = request.json['passw_hash']
			Role_id    = request.json['role_id']
			University_id = request.json['university_id']
			FirstName     = request.json['FirstName']
			SurName       = request.json['SurName']
			SecondName    = request.json['SecondName']


			alikeUs = cursor.execute('select count(*) from users \
			where login="%s"'%Login)
			for u in alikeUs:
				pass
			u = u[0]
			if u > 0:
				return {'status':'loginError'}

			Uid = cursor.execute('select max(id) from users')
			for Uuid in Uid:
				pass 
			Uuid = int(Uuid[0]) + 1
			new_user = User([Uuid, Role_id, University_id,
			FirstName, SurName, SecondName, Login, Passw_hash])
			insertIntoTable(cursor, 'users', new_user.addToDB())
			conn.commit()
			return {'status':'success'}			
		except Exception as e:
			return {'status':'loginError'}


class UserById(Resource):
	def get(self, user_id):
		conn = sqlite3.connect('lbg.db')
		cursor = conn.cursor()
		user = cursor.execute('select * from users where id=%s'%int(user_id))
		for u in user:
			result = User(u)
		return jsonify(result.getFullInfo())


class UserByLogin(Resource):
	def post(self):
		try:
			conn = sqlite3.connect('lbg.db')
			cursor = conn.cursor()
			passw_hash = request.json['passw_hash']
			user_login = request.json['login']
			user = cursor.execute('select * from users where login="%s" and passw_hash="%s"'
				%(user_login, passw_hash))
			for u in user:
				result = User(u)
			return jsonify(result.getFullInfo())
		except:
			return {'status': 'error'}



#TODO: Добавить проверку на дату(Устарело или нет)
class EventByTags(Resource):
	def post(self):
		try:
			conn = sqlite3.connect('lbg.db')
			cursor = conn.cursor()
			tags = request.json['tags']
			events = []
			e = cursor.execute("""select * from calendar  where (select DATETIME('now','localtime')<dt)order by dt asc""")
			for event in e:
				ce = Event(event)
				ce_tags = json.loads(ce.tags)["tags"]
				z = False
				for tag in tags:
					for ce_tag in ce_tags:
						if tag == ce_tag:
							z = True
							events.append(ce.getFullInfo())
							break
					if z == True:
						break
			return jsonify(events)
		except:
			return [{'status': 'error'}]



class AddMessage(Resource):
	def post(self):
		print(request.json)
		conn = sqlite3.connect('lbg.db')
		cursor = conn.cursor()

		mId = cursor.execute('select max(id) from messages')
		for MmId in mId:
			pass 
		if MmId[0] is not None:
			mId = int(MmId[0]) + 1
		else:
			mId = 0

		message_text = request.json['message_text']
		sender_id = request.json['sender_id']
		sender_login = request.json['sender_login']
		dt = request.json['dt']

		m = Message([mId, dt, sender_id, message_text, sender_login])
		insertIntoTable(cursor, 'messages', m.addToDB())
		conn.commit()
		return jsonify(m.getFullInfo())


class AddEvent(Resource):
	def post(self):
		print(request.json)
		conn = sqlite3.connect('lbg.db')
		cursor = conn.cursor()

		mId = cursor.execute('select max(id) from calendar')
		for MmId in mId:
			pass 
		if MmId[0] is not None:
			mId = int(MmId[0]) + 1
		else:
			mId = 0

		title = request.json['title']
		dt = request.json['dt']
		about = request.json['about']
		tags = request.json['tags']

		#m = Event([mId, title, dt, about, tags])
		
		s = ""
		for tag in tags:
			s += '"' + tag +'", '
		s = s[:-2]
		cursor.execute('insert into calendar values(?,?,?,?,?)', (str(mId), title, 
		dt, about,'{"tags":[%s]}'%s))
		#insertIntoTable(cursor, 'calendar', m.addToDB())
		conn.commit()
		#print(m.getFullInfo())
		#return jsonify(m.getFullInfo())


class GetMessages(Resource):
	def post(self):
		conn = sqlite3.connect('lbg.db')
		cursor = conn.cursor()

		messages = cursor.execute('select * from messages')
		
		res = []
		for message in messages:
			m = Message(message)
			res.append(m.getFullInfo())
		return jsonify(res)


class GetUnivs(Resource):
	def get(self):
		conn = sqlite3.connect('lbg.db')
		cursor = conn.cursor()

		univs = cursor.execute('select * from universities')
		
		res = []
		for univ in univs:
			m = University(univ)
			res.append(m.getFullInfo())
		return jsonify(res)



api.add_resource(UserById,    '/users/<user_id>')
api.add_resource(UserByLogin, '/get_user_by_login')
api.add_resource(UserAdd,     '/user_add')
api.add_resource(EventByTags, '/get_event_by_tags')
api.add_resource(AddMessage,  '/add_message')
api.add_resource(AddEvent,    '/add_event')
api.add_resource(GetMessages, '/get_all_messages')
api.add_resource(GetUnivs,    '/get_univers')



if __name__ == '__main__':
	app.run(host='0.0.0.0', debug=True, port='5002')