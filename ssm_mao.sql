CREATE DATABASE IF NOT EXISTS ssm_mao charset utf8;

USE ssm_mao;

-- 用户表
CREATE TABLE user(
	id VARCHAR(100) PRIMARY KEY COMMENT '用户id',
	name VARCHAR(20) NOT NULL COMMENT '用户名',
	password VARCHAR(20) NOT NULL COMMENT '密码',
	createTime VARCHAR(255) NOT NULL COMMENT '创建时间'
)default charset=utf8;


-- 订单表
CREATE TABLE orderItem(
	id VARCHAR(100) PRIMARY KEY COMMENT '订单id',
	address VARCHAR(255) NOT NULL COMMENT '地址',
	recipient VARCHAR(255) NOT NULL COMMENT '收件人',
	phone VARCHAR(255) NOT NULL COMMENT '手机号',
	userMessage VARCHAR(255) COMMENT '买家留言',
	createDate VARCHAR(255) NOT NULL COMMENT '创建时间',
	payDate VARCHAR(255) COMMENT '付款时间',
	deliveryDate VARCHAR(255) COMMENT '发货时间',
	payStatus int(10) DEFAULT 0 COMMENT '0为未付款， 1为已付款',
	deliveryStatus int(10) DEFAULT 0 COMMENT '0为未发货， 1为已发货',
	total FLOAT DEFAULT 0 COMMENT '总数',
	uid VARCHAR(100) COMMENT '用户id',
	FOREIGN KEY(uid) REFERENCES user(id)
)default charset=utf8;

-- 分类表
CREATE TABLE category(
	id INT UNSIGNED PRIMARY KEY auto_increment COMMENT '分类id',
	name VARCHAR(255) NOT NULL COMMENT '分类名称',
	hot int(10) NOT NULL default 0 COMMENT '热度，最高10，最低0'
)default charset=utf8 auto_increment=1;

-- 商品表
CREATE TABLE product(
	id VARCHAR(100) PRIMARY KEY COMMENT '商品id',
	name VARCHAR(255) NOT NULL COMMENT '商品名称',
	price FLOAT UNSIGNED NOT NULL COMMENT '商品价格',
	stock INT NOT NULL COMMENT '库存',
	hot int(10) NOT NULL default 0 COMMENT '热度，最高10，最低0',
	cid INT UNSIGNED NOT NULL COMMENT '所属类别',
	FOREIGN KEY(cid) REFERENCES category(id)
)default charset=utf8;

-- 商品图片
CREATE TABLE productImg(
	id INT UNSIGNED PRIMARY KEY auto_increment COMMENT '商品图片id',
	url VARCHAR(255) NOT NULL COMMENT '图片地址',
	pid VARCHAR(255) NOT NULL COMMENT '所属商品',
	FOREIGN KEY(pid) REFERENCES product(id)
)default charset=utf8 auto_increment=1;

-- 订单与商品关联表
CREATE TABLE order_product(
	id int PRIMARY KEY auto_increment COMMENT 'id',
	num int(10) NOT NULL COMMENT '商品数量',
	pid VARCHAR(100) NOT NULL COMMENT '所关联的商品',
	oid VARCHAR(100) NOT NULL COMMENT '所属订单',
	FOREIGN KEY(pid) REFERENCES product(id),
	FOREIGN KEY(oid) REFERENCES orderitem(id)
)default charset=utf8;



-- 后台管理用户表
CREATE TABLE admin_user(
	id VARCHAR(100) PRIMARY KEY COMMENT 'id',
	userName VARCHAR(50) NOT NULL COMMENT '用户名',
	password VARCHAR(50) NOT NULL COMMENT '密码',
	status int DEFAULT 1 COMMENT '状态，0为用户被禁用了，1为用户可用'
)default charset=utf8;


-- 角色表
CREATE TABLE role(
	id VARCHAR(100) PRIMARY KEY COMMENT 'id',
	roleName VARCHAR(50) COMMENT '角色名',
	roleDesc VARCHAR(50) COMMENT '角色描述'
)default charset=utf8;


-- 资源权限表
CREATE TABLE permission(
	id VARCHAR(100) PRIMARY KEY COMMENT 'id',
	permissionName varchar(50) COMMENT '权限名',
	`desc` varchar(255) COMMENT '权限描述'
)default charset=utf8;


-- 用户与角色关联表
CREATE TABLE user_role(
	userId VARCHAR(100),
	roleId VARCHAR(100),
	PRIMARY KEY(userId, roleId),
	FOREIGN KEY(userId) REFERENCES admin_user(id),
	FOREIGN KEY(roleId) REFERENCES role(id)
)default charset=utf8;


-- 角色表与权限表关联
CREATE TABLE role_permission(
	roleId VARCHAR(100),
	permissionId VARCHAR(100),
	PRIMARY KEY(roleId, permissionId),
	FOREIGN KEY(roleId) REFERENCES role(id),
	FOREIGN KEY(permissionId) REFERENCES permission(id)
)default charset=utf8;

-- 插入管理员
INSERT INTO `admin_user` VALUES ('1', 'root', '7410adb249055d9b0b2c048379055849', '1');

-- 插入角色
INSERT INTO `role` VALUES ('1', 'ADMIN', '管理员');
INSERT INTO `role` VALUES ('2', 'COMMON', '普通管理员');


-- 插入权限
INSERT INTO `permission` VALUES ('0f6aa1c66ce711e98676fcaa145a18d7', 'adminUser:update', '修改后台用户');
INSERT INTO `permission` VALUES ('23bf51d46ca311e9a8f9fcaa145a18d7', 'adminPermission:find', '查询权限');
INSERT INTO `permission` VALUES ('23c007cf6ca311e9a8f9fcaa145a18d7', 'adminPermission:save', '添加权限');
INSERT INTO `permission` VALUES ('23c0b5606ca311e9a8f9fcaa145a18d7', 'adminPermission:delete', '删除角色');
INSERT INTO `permission` VALUES ('23c171d06ca311e9a8f9fcaa145a18d7', 'adminRole:save', '添加角色');
INSERT INTO `permission` VALUES ('23c25ef76ca311e9a8f9fcaa145a18d7', 'adminUser:find', '查询后台用户');
INSERT INTO `permission` VALUES ('23c3848f6ca311e9a8f9fcaa145a18d7', 'adminRole:find', '查询角色');
INSERT INTO `permission` VALUES ('23c4820b6ca311e9a8f9fcaa145a18d7', 'adminUser:insert', '添加后台用户');
INSERT INTO `permission` VALUES ('23c604596ca311e9a8f9fcaa145a18d7', 'adminRole:delete', '删除角色');


-- 插入管理员与角色关联数据
INSERT INTO `user_role` VALUES ('1', '1');
INSERT INTO `user_role` VALUES ('1', '2');

-- 插入角色与权限关联数据
INSERT INTO `role_permission` VALUES ('1', '0f6aa1c66ce711e98676fcaa145a18d7');
INSERT INTO `role_permission` VALUES ('1', '23bf51d46ca311e9a8f9fcaa145a18d7');
INSERT INTO `role_permission` VALUES ('1', '23c007cf6ca311e9a8f9fcaa145a18d7');
INSERT INTO `role_permission` VALUES ('1', '23c0b5606ca311e9a8f9fcaa145a18d7');
INSERT INTO `role_permission` VALUES ('1', '23c171d06ca311e9a8f9fcaa145a18d7');
INSERT INTO `role_permission` VALUES ('1', '23c25ef76ca311e9a8f9fcaa145a18d7');
INSERT INTO `role_permission` VALUES ('1', '23c3848f6ca311e9a8f9fcaa145a18d7');
INSERT INTO `role_permission` VALUES ('1', '23c4820b6ca311e9a8f9fcaa145a18d7');
INSERT INTO `role_permission` VALUES ('1', '23c604596ca311e9a8f9fcaa145a18d7');