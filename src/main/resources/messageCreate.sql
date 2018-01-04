DROP TABLE site.message;
CREATE TABLE site.message (
  callingNumber VARCHAR(11) COMMENT '���к���',
  calledNumber  VARCHAR(11) COMMENT '���к���',
  intercepttime TIMESTAMP COMMENT '����ʱ��',
  strategic     VARCHAR(12) COMMENT '���Լ�����',
  strategyid    VARCHAR(24) COMMENT '����ID',
  casemessage   VARCHAR(2) COMMENT '��������',
  interceptinfo VARCHAR(50) COMMENT '������Ϣ',
  messagedetail VARCHAR(500) COMMENT '��������',
  keyword       VARCHAR(200) COMMENT '�ؼ���'
);
TRUNCATE TABLE site.message;
DROP TABLE site.strategyTable;
CREATE TABLE site.strategyTableEx(
  strategies VARCHAR(250) COMMENT '������'
);
TRUNCATE TABLE site.strategyTable;
DROP TABLE site.sogoumess;
CREATE TABLE site.sogoumess(
  timesign VARCHAR(15) comment 'ʱ���',
  sessionid VARCHAR(32) comment 'sessionid',
  name VARCHAR(50) comment '����',
  num VARCHAR(2) comment '���ʴ���',
  querynum VARCHAR(2) comment '�鿴����',
  url VARCHAR(50) comment 'url'
);
TRUNCATE TABLE site.example;
DROP TABLE site.example;
CREATE TABLE site.example(
  keyword       VARCHAR(200) COMMENT '�ؼ���',
  messagedetail VARCHAR(500) COMMENT '��������',
  callingNumber VARCHAR(11) COMMENT '���к���',
  intercepttime TIMESTAMP COMMENT '�澯ʱ��',
  autidtime VARCHAR(20) COMMENT '���ʱ��'
)