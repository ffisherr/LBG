import json


class User():
	def __init__(self, arg):
		self.id, self.role_id, self.university_id, \
		self.FirstName, self.SurName, self.SecondName, \
		 self.login, self.passw_hash = arg

	def getFullInfo(self):
		info = {'id': str(self.id), 
				'login': self.login,
				'passw_hash': self.passw_hash,
				'role_id': str(self.role_id),
				'university_id': self.university_id,
				'FirstName': self.FirstName,
				'SurName': self.SurName,
				'SecondName': self.SecondName,
				'status': 'success'}
		return info

	def addToDB(self):
		result = []
		result.append(self.id)
		result.append(self.role_id)
		result.append(self.university_id)
		result.append(self.FirstName)
		result.append(self.SurName)
		result.append(self.SecondName)
		result.append(self.login)
		result.append(self.passw_hash)
		return result


class University():
	def __init__(self, arg):
		self.id, self.name = arg

	def getFullInfo(self):
		info = {'id': self.id, 
				'name': self.name,
				'status': 'success'}
		return info

	def addToDB(self):
		result = []
		result.append(self.id)
		result.append(self.name)
		return result


class Message():
	def __init__(self, arg):
		self.id, self.dt, self.sender_id, self.message_text, self.sender_login = arg

	def getFullInfo(self):
		info = {'id': self.id, 
				'sender_id': self.sender_id,
				'dt': str(self.dt),
				'message_text': self.message_text,
				'sender_login': self.sender_login,
				'status': 'success'}
		return info

	def addToDB(self):
		result = []
		result.append(self.id)
		result.append(self.dt)
		result.append(self.sender_id)
		result.append(self.message_text)
		result.append(self.sender_login)
		return result

class Event():
	def __init__(self, arg):
		self.id, self.title, self.dt, self.about, self.tags = arg

	def getFullInfo(self):
		info = {'id': self.id, 
				'title': self.title,
				'dt': self.dt,
				'about': self.about,
				'tags': json.loads(self.tags)["tags"],
				'status': 'success'}
		return info

	def addToDB(self):
		result = []
		result.append(self.id)
		result.append(self.title)
		result.append(self.dt)
		result.append(self.about)
		result.append(self.tags)
		return result
