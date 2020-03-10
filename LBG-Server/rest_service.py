import sqlite3
from model import User
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
			Role_id    = request.json['Role_id']

			alikeUs = cursor.execute('select count(*) from users \
			where login="%s"'%Login)
			for u in alikeUs:
				pass
			u = u[0]
			if u > 0:
				return {'status':'error', 'description':'already exists'}

			Uid = cursor.execute('select max(id) from users')
			for Uuid in Uid:
				pass 
			Uuid = int(Uuid[0]) + 1
			new_user = User([Uuid, Login, Passw_hash, Role_id])
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
			print(request.json)
			passw_hash = request.json['passw_hash']
			user_login = request.json['login']
			user = cursor.execute('select * from users where login="%s" and passw_hash="%s"'
				%(user_login, passw_hash))
			for u in user:
				result = User(u)
			return jsonify({'status':'success', 'info':result.getFullInfo()})
		except:
			return {'status': 'error'}


api.add_resource(UserById,    '/users/<user_id>')
api.add_resource(UserByLogin, '/get_user_by_login')
api.add_resource(UserAdd,     '/user_add')


if __name__ == '__main__':
	app.run(host='0.0.0.0', debug=True, port='5002')