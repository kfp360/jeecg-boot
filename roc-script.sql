create table access_token_record
(
    id            bigint auto_increment comment 'id'
        primary key,
    access_token  varchar(128) null comment '访问token',
    refresh_token varchar(128) null comment '刷新token',
    user_id       bigint       null comment '用户id',
    user_type     int(1)       null comment '用户类型  1=用户 2=后台用户',
    expire_time   datetime     null comment '失效时间',
    status        int          null comment '状态  1=有效 0=无效',
    create_time   datetime     null,
    constraint un_access_token
        unique (access_token),
    constraint un_refresh_token
        unique (refresh_token)
);

create table area_info
(
    id             bigint auto_increment comment '自增主键'
        primary key,
    area_code      varchar(20)              null comment '区域code,街道和区code一致',
    area_name      varchar(128)             null,
    level          int                      null comment '地区等级 1=省 2=市 3=区 4=街道',
    parent_id      bigint      default -1   null comment '所属父地区id',
    create_user_id varchar(64) default '-1' null comment '创建人',
    create_time    datetime                 null comment '创建时间',
    update_time    datetime                 null comment '修改时间'
)
    comment '地区信息表' charset = utf8;

create index LEVEL_INDEX
    on area_info (level);

create index NAME_INDEX
    on area_info (area_name);

create index PARENT_INDEX
    on area_info (parent_id);

create table auth_code
(
    id          bigint auto_increment comment '自增主键'
        primary key,
    mobile      varchar(50)  not null comment '手机号',
    auth_code   varchar(20)  not null comment '验证码',
    auth_type   varchar(20)  not null comment '验证码类型',
    expire_time datetime     not null comment '验证码有效截止时间',
    client_ip   varchar(255) not null comment '客户端IP',
    use_status  varchar(255) not null comment '使用状态',
    create_time datetime     not null comment '验证码创建时间',
    update_time datetime     not null comment '修改时间'
)
    comment '短信验证码表' charset = utf8;

create table constr
(
    id                 bigint auto_increment comment '施工记录自增id'
        primary key,
    licence            varchar(64)                        not null comment '施工许可证编号',
    licence_url        varchar(256)                       not null comment '施工许可证照片地址',
    constr_type        bigint                             not null comment '施工类型id',
    company_name       varchar(64)                        not null comment '施工单位',
    reason             varchar(1024)                      not null comment '施工原因',
    position           varchar(1024)                      not null comment '施工位置',
    occupied_area      varchar(1024)                      not null comment '占道区域',
    occupied_days      varchar(1024)                      not null comment '占道时间',
    liabler            varchar(20)                        not null comment '占道负责人',
    contact            varchar(20)                        not null comment '占道负责人联系电话',
    publishers         varchar(20)                        not null comment '发布人员',
    publishers_contact varchar(20)                        null comment '发布联系人电话',
    owner_dept_name    varchar(128)                       null comment '发布人部门名称',
    start_time         date                               not null comment '施工开始时间',
    end_time           date                               not null comment '施工结束时间',
    status             varchar(20)                        not null comment '施工状态 PUBLICITY=施工公示期 PREPARE=施工准备期 CARRY_OUT=施工开展期 START=施工开始期 DELAY=施工延续期 FINISH=施工结束期',
    create_time        datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time        datetime default CURRENT_TIMESTAMP not null comment '修改时间'
)
    comment '施工信息表' charset = utf8;

create index NUMBER_idx
    on constr (licence);

create table constr_area
(
    id          bigint auto_increment comment '施工位置自增id'
        primary key,
    constr_id   bigint                             not null comment '施工记录id',
    area_type   varchar(20)                        not null comment '施工类型(POINT=点施工,LINE=线路施工,POLYGON=面)',
    longitude   double(20, 6)                      not null comment '施工位置经度',
    latitude    double(20, 6)                      not null comment '施工位置纬度',
    route       text                               null comment '施工路径坐标单位',
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time datetime default CURRENT_TIMESTAMP not null comment '修改时间'
)
    comment '施工位置信息表' charset = utf8;

create index CID_INDEX
    on constr_area (constr_id);

create table constr_attach
(
    id          bigint auto_increment comment '施工附件文件自增id'
        primary key,
    key_id      bigint                             not null comment '关联主体记录id(施工相关为施工记录id，隐患相关为隐患记录id)',
    type        varchar(64)                        not null comment '附件类型  IMAGE=疏解图片 ATTACH=施工附件 CLONKIN=施工打卡 REPORT=隐患上报 PROCESS=隐患处理',
    attach_url  varchar(1024)                      not null comment '施工附件url',
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time datetime default CURRENT_TIMESTAMP not null comment '修改时间'
)
    comment '施工相关附件表' charset = utf8;

create index CID_INDEX
    on constr_attach (key_id);

create index TYPE_INDEX
    on constr_attach (type);

create table constr_clock
(
    id             bigint auto_increment comment '施工打卡自增id'
        primary key,
    constr_id      bigint        not null comment '施工记录id',
    constr_area_id bigint        not null comment '施工区域id',
    constr_type    bigint        not null comment '施工类型id',
    clock_node     varchar(64)   not null comment '打卡节点 PUBLICITY=施工公示期 PREPARE=施工准备期 CARRY_OUT=施工开展期 START=施工开始期 DELAY=施工延续期 FINISH=施工结束期',
    user_id        bigint        not null comment '打卡用户id',
    status         varchar(64)   null comment '打卡状态',
    auditor_id     bigint        null comment '审核人id',
    auditor        varchar(20)   null comment '审核人姓名',
    auditor_mobile varchar(20)   null comment '审核人联系电话',
    longitude      double(20, 6) null comment '用户经度',
    latitude       double(20, 6) null comment '用户维度',
    create_time    datetime      not null comment '打卡时间',
    update_time    datetime      not null comment '修改时间'
)
    comment '施工打卡信息表' charset = utf8;

create index CID_INDEX
    on constr_clock (constr_id);

create table constr_danger
(
    id              bigint auto_increment comment '施工隐患自增id'
        primary key,
    constr_id       bigint         not null comment '施工记录id',
    constr_area_id  bigint         null comment '施工区域id',
    licence         varchar(64)    not null comment '施工许可证编号',
    company_name    varchar(64)    not null comment '施工单位',
    description     varchar(10240) not null comment '隐患描述',
    reporter_id     bigint         null comment '隐患上报人id',
    reporter        varchar(20)    null comment '隐患上报人姓名',
    verifier_id     bigint         null comment '隐患核实人id',
    verifier        varchar(20)    null comment '隐患核实人姓名',
    auditor_id      bigint         null comment '隐患审核人id',
    auditor         varchar(20)    null comment '隐患审核人姓名',
    auditor_mobile  varchar(20)    null comment '隐患审核人联系电话',
    auditor_time    datetime       null comment '审核时间',
    verification_id bigint         null comment '隐患核销人id',
    verification    varchar(20)    null comment '隐患核销人姓名',
    determine       varchar(1024)  null comment '隐患定性说明',
    determine_url   varchar(1024)  null comment '隐患定性文件',
    rectification   varchar(1024)  null comment '隐患整改说明',
    image_url       varchar(256)   null comment '隐患封面图',
    user_lon        double(20, 6)  null comment '用户提交隐患时所在经度',
    user_lat        double(20, 6)  null comment '用户提交隐患时所在纬度',
    danger_status   varchar(64)    not null comment '隐患状态 PROVING=待核实 WAIT_REORGANIZE=待整改 REORGANIZE=整改中 RECTIFIED=待核销 REJECTED=驳回 COMPLETED=完结',
    handler_status  varchar(64)    null comment '隐患处理状态 RECTIFY=整改 PUNISH=处罚',
    create_time     datetime       not null comment '创建时间',
    update_time     datetime       not null comment '修改时间'
)
    comment '施工隐患信息表' charset = utf8;

create index CID_INDEX
    on constr_danger (constr_id);

create table constr_delay
(
    id              bigint auto_increment comment '施工延期自增id'
        primary key,
    constr_id       bigint        not null comment '施工记录id',
    constr_type     bigint        not null comment '施工类型id',
    company_name    varchar(64)   not null comment '施工单位',
    status          varchar(64)   not null comment '延期状态  APPROVAL=申请中 REJECT=驳回 ACCEPT=同意',
    expires_reason  varchar(1024) not null comment '延期原因',
    auditor_id      bigint        null comment '审核人id',
    auditor         varchar(20)   null comment '审核人姓名',
    auditor_mobile  varchar(20)   null comment '审核人联系电话',
    source_end_time date          null comment '原结束时间',
    expires_time    date          not null comment '延期至',
    create_time     datetime      not null comment '延期申请时间',
    update_time     datetime      not null comment '修改时间'
)
    comment '施工延期记录表' charset = utf8;

create index CID_INDEX
    on constr_delay (constr_id);

create table constr_import
(
    id          int auto_increment comment 'id'
        primary key,
    licence     varchar(64) null comment '许可证号',
    import_data text        null comment '导入数据',
    user_id     bigint      null comment '用户id',
    create_time datetime    null comment '创建时间'
)
    comment '施工信息导入记录';

create table constr_street
(
    id           bigint auto_increment comment '自增主键'
        primary key,
    constr_id    bigint                             not null comment '施工id',
    province_id  bigint                             not null comment '省id',
    municipal_id bigint                             not null comment '城市id',
    region_id    bigint                             not null comment '区域id',
    street_id    bigint                             not null comment '街道id',
    create_time  datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time  datetime default CURRENT_TIMESTAMP not null comment '修改时间',
    constraint ID_UNIQUE
        unique (constr_id, region_id, street_id)
)
    comment '施工街道信息表' charset = utf8;

create index C_INDEX
    on constr_street (constr_id);

create index KEY_INDEX
    on constr_street (street_id);

create index TYPE_INDEX
    on constr_street (region_id);

create table constr_type
(
    id          bigint auto_increment comment '施工类型自增主键'
        primary key,
    type_name   varchar(128)                       not null comment '施工类型名称',
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time datetime default CURRENT_TIMESTAMP not null comment '修改时间',
    constraint NUMBER_UNIQUE
        unique (type_name)
)
    comment '施工类型表' charset = utf8;

create table department
(
    id              bigint auto_increment comment '部门自增主键'
        primary key,
    parent_class_id bigint   default -1                not null comment '所属一级部门',
    dept_name       varchar(64)                        not null comment '部门名',
    create_time     datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time     datetime default CURRENT_TIMESTAMP not null comment '修改时间'
)
    comment '部门表' charset = utf8;

create index PID_INDEX
    on department (parent_class_id);

create table exchange
(
    id             bigint auto_increment comment '礼品兑换自增id'
        primary key,
    user_id        bigint      not null comment '用户id',
    province_id    bigint      null comment '省id',
    municipal_id   bigint      null comment '市id',
    region_id      bigint      not null comment '兑换礼品区域',
    street_id      bigint      not null comment '兑换礼品街道',
    gift_id        bigint      not null comment '礼品id',
    gift_name      varchar(64) not null comment '礼品',
    score          bigint      not null comment '礼品兑换所需积分',
    status         varchar(64) not null comment '审核状态 APPROVAL=申请中 REJECT=驳回 ACCEPT=同意',
    auditor_id     bigint      null comment '审核人id',
    auditor        varchar(20) null comment '审核人姓名',
    auditor_mobile varchar(20) null comment '审核人联系电话',
    create_time    datetime    not null comment '申请时间',
    update_time    datetime    not null comment '修改时间'
)
    comment '礼品兑换记录表' charset = utf8;

create index NUMBER_INDEX
    on exchange (user_id);

create table gift
(
    id          bigint auto_increment comment '礼品自增id'
        primary key,
    gift_name   varchar(64) null comment '礼品名称',
    score       bigint      null comment '礼品兑换所需积分',
    is_deleted  varchar(2)  null comment '是否删除 1 已删除  0 未删除',
    create_time datetime    null comment '创建时间',
    update_time datetime    null comment '修改时间'
)
    comment '礼品信息表' charset = utf8;

create index NAME_INDEX
    on gift (gift_name);

create table gift_area
(
    id           bigint auto_increment comment '主键ID'
        primary key,
    gift_id      bigint                             not null comment '礼品ID',
    province_id  bigint                             null,
    municipal_id bigint(32)                         null comment '用户所在城市',
    region_id    bigint                             null comment '区域ID',
    street_id    bigint                             null comment '街道ID',
    create_time  datetime default CURRENT_TIMESTAMP null comment '创建时间'
)
    comment '礼品兑换区域';

create table gift_attach
(
    id          bigint auto_increment comment '主键ID'
        primary key,
    attach_url  varchar(255)                       null comment '文件地址',
    gift_id     bigint                             null,
    create_time datetime default CURRENT_TIMESTAMP null comment '创建时间'
)
    comment '礼品附件图片';

create table integral_flow
(
    id              bigint auto_increment comment '积分变动自增id'
        primary key,
    user_id         bigint      not null comment '用户id',
    key_id          bigint      not null comment '积分变动事件关联主体id(隐患id、礼品兑换记录id)',
    type            varchar(64) not null comment '积分变动类型 EXCHANGE_GIFT=兑换物品 DANGER_AWARD=隐患奖励 EXCHANGE_BACK=兑换物品退回 ',
    score           bigint      not null comment '积分变动值',
    auditor_id      bigint      null comment '兑换审核人',
    auditor         varchar(64) null comment '兑换审核人联系电话',
    exchange_status varchar(64) null comment '兑换状态(仅兑换记录需要该属性)',
    create_time     datetime    not null comment '积分变动时间',
    update_time     datetime    not null comment '修改时间'
)
    comment '积分变动记录表' charset = utf8;

create index NUMBER_INDEX
    on integral_flow (user_id);

create table relevance_constr
(
    id          bigint auto_increment comment '施工打卡自增id'
        primary key,
    user_id     bigint      not null comment '施工类型用户id',
    constr_id   bigint      not null comment '施工记录id',
    status      varchar(20) not null comment '状态 NORMAL=正常关注  INVALID=无效关注',
    create_time datetime    not null comment '创建时间',
    update_time datetime    not null comment '修改时间'
)
    comment '施工人员关注施工关联表' charset = utf8;

create index CID_INDEX
    on relevance_constr (constr_id);

create table report
(
    id              bigint auto_increment comment '报表发送自增id'
        primary key,
    user_id         bigint       not null comment '发件人id',
    start_date      date         not null comment '统计开始日期',
    end_date        date         not null comment '统计结束日期',
    region_ids      varchar(128) not null comment '区域ids',
    region_names    varchar(512) not null comment '区域名字列表',
    street_ids      varchar(128) not null comment '街道ids',
    street_names    varchar(512) not null comment '街道列表',
    receive_email   varchar(512) not null comment '接收邮件人',
    statistics_type varchar(128) not null comment '统计类型 LICENCE=许可,PUNISH=处罚,RECTIFY=整改,DANGER=隐患,CLOCK=打卡,INTEGRAL=积分',
    file_type       varchar(64)  not null comment '文件类型  WORD,EXCEL,PDF,CSV',
    send_status     varchar(8)   null comment '发送状态',
    create_time     datetime     not null comment '创建时间',
    update_time     datetime     not null comment '修改时间'
)
    comment '报表发送记录表' charset = utf8;

create index NUMBER_INDEX
    on report (user_id);

create table roc_admin
(
    id          bigint auto_increment comment '自增主键'
        primary key,
    name        varchar(128)                       not null comment '姓名',
    username    varchar(64)                        not null comment '账号',
    telphone    varchar(16)                        not null comment '手机号',
    password    varchar(64)                        not null comment '密码',
    status      varchar(20)                        not null comment '隐患处理状态',
    update_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    expire_time datetime default CURRENT_TIMESTAMP not null comment '修改时间',
    constraint un_username
        unique (username)
)
    comment '管理员表' charset = utf8;

create table system_config
(
    id             bigint auto_increment comment '主键'
        primary key,
    config_name    varchar(64)   null comment '配置名称',
    config_key     varchar(64)   null comment '配置key',
    config_value   varchar(1024) null comment '配置value',
    param_json     varchar(1024) null comment '参数JSON',
    config_example varchar(64)   null comment '示例',
    config_desc    varchar(64)   null comment '说明',
    create_time    datetime      null comment '创建时间',
    update_time    datetime      null comment '修改时间'
);

create table third_user
(
    id      bigint auto_increment
        primary key,
    user_id bigint      default -1     null comment '关联用户id',
    type    varchar(16) default 'WX'   null comment '三方类型',
    open_id varchar(64) charset utf8   not null comment '令牌(微信)',
    wx_nick varchar(1024) charset utf8 null comment '微信昵称',
    wx_data varchar(1024) charset utf8 null comment '微信备用字段',
    constraint un_open_id
        unique (open_id)
);

create table user_auth_record
(
    id              bigint auto_increment comment '用户认证记录id'
        primary key,
    user_id         bigint       not null comment '用户id',
    user_type       varchar(255) not null comment '用户类型',
    province_id     bigint       null comment '用户所在省份',
    municipal_id    bigint(32)   null comment '用户所在城市',
    parent_class_id bigint       null comment '用户所属一级部门',
    class_id        bigint       null comment '用户所属二级部门',
    child_class_id  bigint       null comment '用户所属三级部门',
    company_name    varchar(64)  null comment '用户所属公司',
    auth_status     varchar(20)  null comment '用户认证状态 ACCEPT=通过 REJECT=驳回',
    create_time     datetime     not null comment '创建时间',
    update_time     datetime     not null comment '修改时间'
)
    comment '用户认证记录表' charset = utf8;

create index UID_INDEX
    on user_auth_record (user_id);

create table user_exception
(
    id             bigint auto_increment comment '主键ID'
        primary key,
    user_id        bigint                             not null comment '用户ID',
    user_name      varchar(255)                       null comment '用户名称',
    head_img       varchar(255)                       null comment '用户头像',
    user_dept      varchar(20)                        null comment '所在部门',
    user_dept_name varchar(255)                       null comment '部门名称',
    create_time    datetime default CURRENT_TIMESTAMP null comment '创建时间'
)
    comment '异常用户信息';

create table user_follow
(
    id           bigint auto_increment comment '自增主键'
        primary key,
    user_id      bigint                 not null comment '用户id',
    province_id  bigint                 not null comment '省id',
    municipal_id bigint                 not null comment '城市id',
    region_id    bigint                 not null comment '区域id',
    street_id    bigint                 not null comment '街道id',
    status       varchar(20) default '' not null comment '用户关注街道的有效状态(EFFECTIVE=有效，UNEFFECTIVE=无效)',
    create_time  datetime               not null comment '创建时间',
    update_time  datetime               not null comment '修改时间'
)
    comment '用户关注的地区信息表' charset = utf8;

create index KEY_INDEX
    on user_follow (street_id);

create index TYPE_INDEX
    on user_follow (region_id);

create index UID_INDEX
    on user_follow (user_id);

create table user_info
(
    id              bigint auto_increment comment '用户自增id'
        primary key,
    name            varchar(64)      null comment '用户姓名',
    mobile_phone    varchar(20)      null comment '用户手机号',
    login_pwd       varchar(128)     null comment '用户密码',
    user_type       varchar(64)      not null comment '用户类型 CITIZEN=市民 SUPERVISOR=监管  CONSTR=施工 ADMIN=行政管理',
    head_img        varchar(1024)    null comment '用户头像url',
    province_id     varchar(20)      null comment '用户所在省份',
    municipal_id    varchar(32)      null comment '用户所在城市',
    parent_class_id bigint           null comment '用户所属一级部门',
    class_id        bigint           null comment '用户所属二级部门',
    child_class_id  bigint           null comment '用户所属三级部门',
    company_name    varchar(64)      null comment '用户所属公司',
    auth_status     varchar(20)      null comment '用户认证状态 UNCERTIFIED=未认证 CERTIFIED=已经认证',
    freeze          varchar(20)      null comment '用户可用状态',
    open_id         varchar(64)      null comment '令牌(微信)',
    wx_nick         varchar(64)      null comment '微信昵称',
    wx_data         varchar(1024)    null comment '微信备用字段',
    integral        bigint default 0 null comment '用户积分',
    create_time     datetime         not null comment '创建时间',
    update_time     datetime         not null comment '修改时间',
    constraint MOBILE_UNIQUE
        unique (mobile_phone),
    constraint WX_UNIQUE
        unique (open_id)
)
    comment '用户信息表' charset = utf8;

create table user_login_record
(
    id          bigint auto_increment comment 'id'
        primary key,
    user_id     bigint      null comment '用户id',
    login_ip    varchar(20) null comment '登录ip',
    create_time datetime    null comment '创建时间',
    update_time datetime    null comment '修改时间'
)
    comment '用户登录记录';

create table user_report_param
(
    id              bigint auto_increment comment 'id'
        primary key,
    user_id         bigint       null comment '用户id',
    publicity_param varchar(128) null comment '公示信息',
    danger_param    varchar(128) null comment '隐患信息',
    punish_param    varchar(128) null comment '触发信息',
    rectify_param   varchar(128) null comment '整改参数',
    clock_param     varchar(128) null comment '打卡信息',
    integra_param   varchar(128) null comment '积分信息',
    file_format     varchar(10)  null comment '文件格式 WORD,EXCEL',
    status          varchar(10)  null comment '状态  NORMAL=正常  INVALID=无效',
    create_time     datetime     null comment '创建时间',
    update_time     datetime     null comment '修改时间'
)
    comment '用户报表参数';


