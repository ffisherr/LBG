import json


class User():
	def __init__(self, arg):
		self.id, self.login, self.passw_hash, self.role_id = arg

	def getFullInfo(self):
		info = {'id': str(self.id), 
				'login': self.login,
				'passw_hash': self.passw_hash,
				'Role_id': str(self.role_id),
				'status': 'success'}
		return info

	def addToDB(self):
		result = []
		result.append(self.id)
		result.append(self.login)
		result.append(self.passw_hash)
		result.append(self.role_id)
		return result


def Message(self):
	def __init__(self, arg):
		self.id, self.dt, self.sender_id, self.message_text = arg

	def getFullInfo(self):
		info = {'id': self.id, 
				'sender_id': self.sender_id,
				'dt': self.dt,
				'message_text': self.message_text,
				'status': 'success'}
		return info

	def addToDB(self):
		result = []
		result.append(self.id)
		result.append(self.dt)
		result.append(self.sender_id)
		result.append(self.message_text)
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
