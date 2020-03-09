class User():
	def __init__(self, arg):
		self.id, self.login, self.passw_hash, self.role_id = arg

	def getFullInfo(self):
		info = {'id': self.id, 
				'login': self.login,
				'passw_hash': self.passw_hash,
				'role_id': self.role_id}
		return info

	def addToDB(self):
		result = []
		result.append(self.id)
		result.append(self.login)
		result.append(self.passw_hash)
		result.append(self.role_id)
		return result
		