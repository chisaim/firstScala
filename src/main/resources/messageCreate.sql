DROP TABLE site.message;
CREATE TABLE site.message (
  callingNumber VARCHAR(11) COMMENT '主叫号码',
  calledNumber  VARCHAR(11) COMMENT '被叫号码',
  intercepttime TIMESTAMP COMMENT '拦截时间',
  strategic     VARCHAR(12) COMMENT '策略集编码',
  strategyid    VARCHAR(24) COMMENT '策略ID',
  casemessage   VARCHAR(2) COMMENT '级联短信',
  interceptinfo VARCHAR(50) COMMENT '拦截信息',
  messagedetail VARCHAR(500) COMMENT '短信内容',
  keyword       VARCHAR(200) COMMENT '关键字'
);
TRUNCATE TABLE site.message;
DROP TABLE site.strategyTable;
CREATE TABLE site.strategyTableEx(
  strategies VARCHAR(250) COMMENT '主策略'
);
TRUNCATE TABLE site.strategyTable;
DROP TABLE site.sogoumess;
CREATE TABLE site.sogoumess(
  timesign VARCHAR(15) comment '时间戳',
  sessionid VARCHAR(32) comment 'sessionid',
  name VARCHAR(50) comment '名称',
  num VARCHAR(2) comment '访问次数',
  querynum VARCHAR(2) comment '查看次数',
  url VARCHAR(50) comment 'url'
);
TRUNCATE TABLE site.example;
DROP TABLE site.example;
CREATE TABLE site.example(
  keyword       VARCHAR(200) COMMENT '关键字',
  messagedetail VARCHAR(500) COMMENT '短信内容',
  callingNumber VARCHAR(11) COMMENT '主叫号码',
  intercepttime TIMESTAMP COMMENT '告警时间',
  autidtime VARCHAR(20) COMMENT '审核时间'
)