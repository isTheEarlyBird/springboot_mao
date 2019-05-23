CREATE DATABASE IF NOT EXISTS ssm_mao charset utf8;

USE ssm_mao;

-- �û���
CREATE TABLE user(
	id VARCHAR(100) PRIMARY KEY COMMENT '�û�id',
	name VARCHAR(20) NOT NULL COMMENT '�û���',
	password VARCHAR(20) NOT NULL COMMENT '����',
	createTime VARCHAR(255) NOT NULL COMMENT '����ʱ��'
)default charset=utf8;


-- ������
CREATE TABLE orderItem(
	id VARCHAR(100) PRIMARY KEY COMMENT '����id',
	address VARCHAR(255) NOT NULL COMMENT '��ַ',
	recipient VARCHAR(255) NOT NULL COMMENT '�ռ���',
	phone VARCHAR(255) NOT NULL COMMENT '�ֻ���',
	userMessage VARCHAR(255) COMMENT '�������',
	createDate VARCHAR(255) NOT NULL COMMENT '����ʱ��',
	payDate VARCHAR(255) COMMENT '����ʱ��',
	deliveryDate VARCHAR(255) COMMENT '����ʱ��',
	payStatus int(10) DEFAULT 0 COMMENT '0Ϊδ��� 1Ϊ�Ѹ���',
	deliveryStatus int(10) DEFAULT 0 COMMENT '0Ϊδ������ 1Ϊ�ѷ���',
	total FLOAT DEFAULT 0 COMMENT '����',
	uid VARCHAR(100) COMMENT '�û�id',
	FOREIGN KEY(uid) REFERENCES user(id)
)default charset=utf8;

-- �����
CREATE TABLE category(
	id INT UNSIGNED PRIMARY KEY auto_increment COMMENT '����id',
	name VARCHAR(255) NOT NULL COMMENT '��������',
	hot int(10) NOT NULL default 0 COMMENT '�ȶȣ����10�����0'
)default charset=utf8 auto_increment=1;

-- ��Ʒ��
CREATE TABLE product(
	id VARCHAR(100) PRIMARY KEY COMMENT '��Ʒid',
	name VARCHAR(255) NOT NULL COMMENT '��Ʒ����',
	price FLOAT UNSIGNED NOT NULL COMMENT '��Ʒ�۸�',
	stock INT NOT NULL COMMENT '���',
	hot int(10) NOT NULL default 0 COMMENT '�ȶȣ����10�����0',
	cid INT UNSIGNED NOT NULL COMMENT '�������',
	FOREIGN KEY(cid) REFERENCES category(id)
)default charset=utf8;

-- ��ƷͼƬ
CREATE TABLE productImg(
	id INT UNSIGNED PRIMARY KEY auto_increment COMMENT '��ƷͼƬid',
	url VARCHAR(255) NOT NULL COMMENT 'ͼƬ��ַ',
	pid VARCHAR(255) NOT NULL COMMENT '������Ʒ',
	FOREIGN KEY(pid) REFERENCES product(id)
)default charset=utf8 auto_increment=1;

-- ��������Ʒ������
CREATE TABLE order_product(
	id int PRIMARY KEY auto_increment COMMENT 'id',
	num int(10) NOT NULL COMMENT '��Ʒ����',
	pid VARCHAR(100) NOT NULL COMMENT '����������Ʒ',
	oid VARCHAR(100) NOT NULL COMMENT '��������',
	FOREIGN KEY(pid) REFERENCES product(id),
	FOREIGN KEY(oid) REFERENCES orderitem(id)
)default charset=utf8;



-- ��̨�����û���
CREATE TABLE admin_user(
	id VARCHAR(100) PRIMARY KEY COMMENT 'id',
	userName VARCHAR(50) NOT NULL COMMENT '�û���',
	password VARCHAR(50) NOT NULL COMMENT '����',
	status int DEFAULT 1 COMMENT '״̬��0Ϊ�û��������ˣ�1Ϊ�û�����'
)default charset=utf8;


-- ��ɫ��
CREATE TABLE role(
	id VARCHAR(100) PRIMARY KEY COMMENT 'id',
	roleName VARCHAR(50) COMMENT '��ɫ��',
	roleDesc VARCHAR(50) COMMENT '��ɫ����'
)default charset=utf8;


-- ��ԴȨ�ޱ�
CREATE TABLE permission(
	id VARCHAR(100) PRIMARY KEY COMMENT 'id',
	permissionName varchar(50) COMMENT 'Ȩ����',
	`desc` varchar(255) COMMENT 'Ȩ������'
)default charset=utf8;


-- �û����ɫ������
CREATE TABLE user_role(
	userId VARCHAR(100),
	roleId VARCHAR(100),
	PRIMARY KEY(userId, roleId),
	FOREIGN KEY(userId) REFERENCES admin_user(id),
	FOREIGN KEY(roleId) REFERENCES role(id)
)default charset=utf8;


-- ��ɫ����Ȩ�ޱ����
CREATE TABLE role_permission(
	roleId VARCHAR(100),
	permissionId VARCHAR(100),
	PRIMARY KEY(roleId, permissionId),
	FOREIGN KEY(roleId) REFERENCES role(id),
	FOREIGN KEY(permissionId) REFERENCES permission(id)
)default charset=utf8;

-- �������Ա
INSERT INTO `admin_user` VALUES ('1', 'root', '7410adb249055d9b0b2c048379055849', '1');

-- �����ɫ
INSERT INTO `role` VALUES ('1', 'ADMIN', '����Ա');
INSERT INTO `role` VALUES ('2', 'COMMON', '��ͨ����Ա');


-- ����Ȩ��
INSERT INTO `permission` VALUES ('0f6aa1c66ce711e98676fcaa145a18d7', 'adminUser:update', '�޸ĺ�̨�û�');
INSERT INTO `permission` VALUES ('23bf51d46ca311e9a8f9fcaa145a18d7', 'adminPermission:find', '��ѯȨ��');
INSERT INTO `permission` VALUES ('23c007cf6ca311e9a8f9fcaa145a18d7', 'adminPermission:save', '���Ȩ��');
INSERT INTO `permission` VALUES ('23c0b5606ca311e9a8f9fcaa145a18d7', 'adminPermission:delete', 'ɾ����ɫ');
INSERT INTO `permission` VALUES ('23c171d06ca311e9a8f9fcaa145a18d7', 'adminRole:save', '��ӽ�ɫ');
INSERT INTO `permission` VALUES ('23c25ef76ca311e9a8f9fcaa145a18d7', 'adminUser:find', '��ѯ��̨�û�');
INSERT INTO `permission` VALUES ('23c3848f6ca311e9a8f9fcaa145a18d7', 'adminRole:find', '��ѯ��ɫ');
INSERT INTO `permission` VALUES ('23c4820b6ca311e9a8f9fcaa145a18d7', 'adminUser:insert', '��Ӻ�̨�û�');
INSERT INTO `permission` VALUES ('23c604596ca311e9a8f9fcaa145a18d7', 'adminRole:delete', 'ɾ����ɫ');


-- �������Ա���ɫ��������
INSERT INTO `user_role` VALUES ('1', '1');
INSERT INTO `user_role` VALUES ('1', '2');

-- �����ɫ��Ȩ�޹�������
INSERT INTO `role_permission` VALUES ('1', '0f6aa1c66ce711e98676fcaa145a18d7');
INSERT INTO `role_permission` VALUES ('1', '23bf51d46ca311e9a8f9fcaa145a18d7');
INSERT INTO `role_permission` VALUES ('1', '23c007cf6ca311e9a8f9fcaa145a18d7');
INSERT INTO `role_permission` VALUES ('1', '23c0b5606ca311e9a8f9fcaa145a18d7');
INSERT INTO `role_permission` VALUES ('1', '23c171d06ca311e9a8f9fcaa145a18d7');
INSERT INTO `role_permission` VALUES ('1', '23c25ef76ca311e9a8f9fcaa145a18d7');
INSERT INTO `role_permission` VALUES ('1', '23c3848f6ca311e9a8f9fcaa145a18d7');
INSERT INTO `role_permission` VALUES ('1', '23c4820b6ca311e9a8f9fcaa145a18d7');
INSERT INTO `role_permission` VALUES ('1', '23c604596ca311e9a8f9fcaa145a18d7');