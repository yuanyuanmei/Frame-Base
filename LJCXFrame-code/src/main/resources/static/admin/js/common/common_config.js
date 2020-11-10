/**
 *  系统请求路径
 */
var requestConfig = {
	loginUrl:{
		"login" : "login",
		"logout" : "logout",
	},
	userUrl : {
		"search" : "user/pageList",
		"isExist" : "account/isExist",
        "findByRole" : "user/findByRole",
        "list": "user/list",
		"save":"account/save",
		"update":"user/update",
		"del":"user/del",
		"updateState":"user/updateStatus",
	},
	roleUrl : {
		"search" : "role/pageList",
		"addRole" : "role/save",
		"editRole" : "role/save",
		"deleteRole" : "role/del",
		"delRoleUser" : "role/delRoleUser",
		"addRoleUser" : "role/addRoleUser",
		"userRoleList" : "role/userRoleList",
	},
	dict : {
		"bindTree" : "sys/dic/treeList",
		"search" : "sys/dic/pageList",
        "itemByType" : "sys/dic/itemByType",
        "save" : "sys/dic/save",
        "update" : "sys/dic/update",
        "delete" : "sys/dic/del",

	},
	menu : {
		"addMenu" : "sys/menu/save",
		"editMenu" : "sys/menu/update",
		"bindTree" : "sys/menu/bindTree",
		"treeTable" : "sys/menu/treeTable",
		"deleteMenu" : "sys/menu/delete",
		"getMenu": "sys/menu/info",
		"bindAllTree":"sys/menu/bindAllTree"
	},
	perms:{
		"search" : "permissions/pageList",
		"treeList" : "permissions/treeList",
		"getRolePerms" : "permissions/queryByRoleId"
	},
	teamUrl:{
		"teamList":"team/list/list",
		"treeList":"team/list/treeList",
		"saveTeam":"team/list/save",
		"teamTree":"team/list/treeList",
		"getTeamList":"team/list/list",
		"del":"team/list/del",
		"uavList":"team/uav/pageList",
		"carList":"team/car/pageList",
		"bindTeam":"team/list/bindTeam",
		"cancelTeam":"team/list/cancelTeam",
		"saveUav":"team/uav/save",
		"saveCar":"team/car/save",
		"delUav":"team/uav/del",
		"delCar":"team/car/del",
		"equipmentList":"team/list/equipmentList"

	},
	dataUrl:{
		"flyareaList":"data/flyarea/pageList",
		"panoramaList":"data/panorama/pageList",
		"scenereportList":"data/scenereport/pageList",
		"layerList":"data/layer/pageList",
		"saveFly":"data/flyarea/save",
		"savePan":"data/panorama/save",
		"saveSce":"data/scenereport/save",
		"saveLayer":"data/layer/save",
		"delFly":"data/flyarea/del",
		"delPan":"data/panorama/del",
		"delSce":"data/scenereport/del",
		"delLayer":"data/layer/del",
		"flyTypeList":"data/flyarea/enumsList",
		"sceTypeList":"data/scenereport/enumsList",

	},
	sysUrl:{
		"fileList":"sys/file/pageList",
		"upload":"sys/file/upload",
		"download":"sys/file/download",
		"resource":"sys/file/resource",
		"delFile":"sys/file/del",
	},
	taskUrl:{
		"taskList":"task/list/pageList",
		"saveTask":"task/list/save",
		"delTask":"task/list/del",
		"recordsList":"task/list/recordsList",
		"taskTypeList":"task/list/enumsList",
	},
	apkUrl:{
		"update":"apk/update",
		"pageList":"apk/pageList",
		"upload":"apk/upload",
	},

	monitorUrl:{
		"jobList":"monitor/job/list",
		"editSave":"monitor/job/edit",
		"add":"monitor/job/add",

	},
	
};
