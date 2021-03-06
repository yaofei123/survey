CREATE TABLE [survey].[Link] (
	[l_id] [numeric](18, 0) IDENTITY (1, 1) NOT NULL ,
	[l_url] [varchar] (500) COLLATE Chinese_PRC_CI_AS NULL ,
	[l_name] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,
	[l_img] [varchar] (500) COLLATE Chinese_PRC_CI_AS NULL ,
	[l_info] [varchar] (1000) COLLATE Chinese_PRC_CI_AS NULL ,
	[l_isLock] [bit] NULL ,
	[l_addtime] [datetime] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [survey].[user] (
	[a_id] [numeric](18, 0) IDENTITY (1, 1) NOT NULL ,
	[username] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,
	[a_pass] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,
	[a_name] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,
	[a_isLock] [bit] NULL ,
	[a_lastLogTime] [datetime] NULL ,
	[a_logTimes] [numeric](18, 0) NULL ,
	[a_loginIp] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,
	[a_addtime] [datetime] NULL ,
	[a_email] [varchar] (100) COLLATE Chinese_PRC_CI_AS NULL ,
	[a_info] [varchar] (1000) COLLATE Chinese_PRC_CI_AS NULL 
) ON [PRIMARY]
GO

CREATE TABLE [survey].[answersheet] (
	[as_id] [numeric](18, 0) IDENTITY (1, 1) NOT NULL ,
	[s_id] [numeric](18, 0) NOT NULL ,
	[as_result] [varchar] (8000) COLLATE Chinese_PRC_CI_AS NULL ,
	[as_postdate] [datetime] NULL ,
	[as_userIP] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL 
) ON [PRIMARY]
GO

CREATE TABLE [survey].[config] (
	[id] [int] NOT NULL ,
	[c_siteName] [varchar] (500) COLLATE Chinese_PRC_CI_AS NULL ,
	[c_siteURL] [varchar] (500) COLLATE Chinese_PRC_CI_AS NULL ,
	[c_isOpen] [bit] NULL ,
	[c_closeWord] [varchar] (1000) COLLATE Chinese_PRC_CI_AS NULL ,
	[copyright] [varchar] (500) COLLATE Chinese_PRC_CI_AS NULL 
) ON [PRIMARY]
GO

CREATE TABLE [survey].[guestbook] (
	[gb_id] [numeric](18, 0) IDENTITY (1, 1) NOT NULL ,
	[gb_user] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,
	[gb_content] [varchar] (5000) COLLATE Chinese_PRC_CI_AS NULL ,
	[gb_phone] [varchar] (20) COLLATE Chinese_PRC_CI_AS NULL ,
	[gb_qq] [varchar] (20) COLLATE Chinese_PRC_CI_AS NULL ,
	[gb_email] [varchar] (100) COLLATE Chinese_PRC_CI_AS NULL ,
	[gb_postdate] [datetime] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [survey].[question] (
	[q_id] [numeric](18, 0) IDENTITY (1, 1) NOT NULL ,
	[s_id] [numeric](18, 0) NOT NULL ,
	[q_type] [numeric](18, 0) NULL ,
	[q_head] [varchar] (1000) COLLATE Chinese_PRC_CI_AS NULL ,
	[q_body] [varchar] (8000) COLLATE Chinese_PRC_CI_AS NULL ,
	[q_result] [varchar] (1000) COLLATE Chinese_PRC_CI_AS NULL ,
	[q_img] [varchar] (1000) COLLATE Chinese_PRC_CI_AS NULL ,
	[q_jdtz] [varchar] (1000) COLLATE Chinese_PRC_CI_AS NULL ,
	[q_order] [numeric](18, 0) NULL 
) ON [PRIMARY]
GO

CREATE TABLE [survey].[survey] (
	[s_id] [numeric](18, 0) IDENTITY (1, 1) NOT NULL ,
	[templet_id] [numeric](18, 0) NOT NULL ,
	[s_name] [varchar] (100) COLLATE Chinese_PRC_CI_AS NULL ,
	[s_desc] [varchar] (500) COLLATE Chinese_PRC_CI_AS NULL ,
	[s_author] [varchar] (100) COLLATE Chinese_PRC_CI_AS NULL ,
	[s_img] [varchar] (1000) COLLATE Chinese_PRC_CI_AS NULL ,
	[s_ipRepeat] [bit] NULL ,
	[s_createDate] [datetime] NULL ,
	[s_ipLimitType] [varchar] (10) COLLATE Chinese_PRC_CI_AS NULL ,
	[s_ipRange] [varchar] (2000) COLLATE Chinese_PRC_CI_AS NULL ,
	[s_password] [varchar] (100) COLLATE Chinese_PRC_CI_AS NULL ,
	[s_isOpen] [bit] NULL ,
	[s_expireDate] [datetime] NULL ,
	[s_isAudited] [bit] NULL ,
	[s_hits] [numeric](18, 0) NULL ,
	[s_usehits] [numeric](18, 0) NULL 
) ON [PRIMARY]
GO

CREATE TABLE [survey].[templet] (
	[templet_id] [numeric](18, 0) IDENTITY (1, 1) NOT NULL ,
	[templet_name] [varchar] (100) COLLATE Chinese_PRC_CI_AS NULL ,
	[templet_top] [varchar] (8000) COLLATE Chinese_PRC_CI_AS NULL ,
	[templet_body] [varchar] (8000) COLLATE Chinese_PRC_CI_AS NULL ,
	[templet_bottom] [varchar] (8000) COLLATE Chinese_PRC_CI_AS NULL ,
	[templet_default] [bit] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [survey].[text] (
	[t_id] [numeric](18, 0) IDENTITY (1, 1) NOT NULL ,
	[q_id] [numeric](18, 0) NOT NULL ,
	[t_content] [varchar] (8000) COLLATE Chinese_PRC_CI_AS NULL 
) ON [PRIMARY]
GO

