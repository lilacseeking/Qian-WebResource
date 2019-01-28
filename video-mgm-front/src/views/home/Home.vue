<template>
	<el-row class="container">
		<el-col :span="24" class="header">
			<el-col :span="8" class="logo" :class="collapsed?'logo-collapse-width':'logo-width'">
				{{collapsed?'':sysName}}
			</el-col>
			<el-col :span="5">
				<div class="tools" @click.prevent="collapse">
					<i :class="collapsed?'el-icon-d-arrow-right':'el-icon-d-arrow-left'"></i>
				</div>
			</el-col>
			<el-col :span="11" class="userinfo">
                <span class="tools" style="width: auto" v-text="systemTopTxt"></span>
				<el-dropdown trigger="hover">
					<span class="el-dropdown-link userinfo-inner"> <img src="../../assets/logo2.png"/>{{sysUserName}}</span>
					<el-dropdown-menu slot="dropdown">
						<el-dropdown-item>我的消息:无</el-dropdown-item>
						<el-dropdown-item divided @click.native="logout">退出登录</el-dropdown-item>
					</el-dropdown-menu>
				</el-dropdown>
			</el-col>
		</el-col>
		<el-col :span="24" class="main">
			<aside :class="collapsed?'menu-collapsed':'menu-expanded'">
				<!--导航菜单 :default-active="$route.path"-->
				<el-menu :default-active="$route.path.indexOf('mainPartner')>0?'/mainPartner':$route.path" class="el-menu-vertical-demo fixscroll menu-color" @open="handleopen" @close="handleclose" @select="handleselect"
						 unique-opened router v-show="!collapsed" v-if="envDefault">
					<template v-for="(item,index) in $router.options.routes" v-if="!item.hidden">
						<el-submenu :index="index+''" v-if="!item.leaf">
							<template slot="title"><i :class="item.iconCls"></i>{{item.name}}</template>
							<el-menu-item v-for="(child,index) in item.children" :index="child.path" :key="child.path" v-if="!child.hidden" @click="openSub(index,child)" :class="{'is-active':isActive[index]}">{{child.name}}</el-menu-item>
						</el-submenu>
						<el-menu-item v-if="item.leaf&&item.children.length>0" :index="item.children[0].path"><i :class="item.iconCls"></i>{{item.children[0].name}}</el-menu-item>
					</template>
				</el-menu>
				<!--end-->
				<el-menu :default-active="$route.path" router class="el-menu-vertical-demo fixscroll menu-color"
						 @open="handleopen" @close="handleclose" @select="handleselect"
						 unique-opened router v-show="!collapsed" v-if="!envDefault">
					<template v-if="!item.hidden" v-for="(item,index) in menuList">
						<el-submenu :index="index+''">
							<template slot="title"><i :class="item.iconCls?item.iconCls:'el-icon-message'"></i>{{item.menuName}}</template>
							<el-menu-item v-for="(child,index) in item.subMenu" :index="'/'+child.menuRout" :key="'/'+child.menuRout" v-if="!child.hidden" @click="openSub(index,'/'+child.menuRout)" :class="{'is-active':isActive[index]}">
								{{child.menuName}}
							</el-menu-item>
						</el-submenu>
					</template>
				</el-menu>
				<!--导航菜单-折叠后-->
				<ul class="el-menu el-menu-vertical-demo collapsed" v-show="collapsed" ref="menuCollapsed">
					<!--开始-->
					<li v-for="(item,index) in $router.options.routes" v-if="!item.hidden && envDefault" class="el-submenu item">
						<template v-if="!item.leaf">
							<div class="el-submenu__title" style="padding-left: 20px;" @mouseover="showMenu(index,true)" @mouseout="showMenu(index,false)"><i :class="item.iconCls?item.iconCls:'el-icon-message'"></i></div>
							<ul class="el-menu submenu" :class="'submenu-hook-'+index" @mouseover="showMenu(index,true)" @mouseout="showMenu(index,false)">
								<li v-for="(child,index) in item.children" v-if="!child.hidden" :key="child.path" class="el-menu-item" style="padding-left: 40px;" :class="[$route.path==child.path?'is-active':'',{'is-active':isActive[index]}]" @click="openSub(index,child)">{{child.name}}</li>
							</ul>
						</template>
						<template v-else>
							<span class="el-submenu">
								<div class="el-submenu__title el-menu-item" style="padding-left: 20px;height: 56px;line-height: 56px;padding: 0 20px;" :class="$route.path==item.children[0].path?'is-active':''" @click="$router.push(item.children[0].path)"><i :class="item.iconCls"></i></div>
							</span>
						</template>
					</li>
					<!--结束-->
					<li v-for="(item,index) in menuList" v-if="!item.hidden && !envDefault" class="el-submenu item">
						<template v-if="!item.leaf">
							<div class="el-submenu__title" style="padding-left: 20px;" @mouseover="showMenu(index,true)" @mouseout="showMenu(index,false)"><i :class="item.iconCls?item.iconCls:'el-icon-message'"></i></div>
							<ul class="el-menu submenu" :class="'submenu-hook-'+index" @mouseover="showMenu(index,true)" @mouseout="showMenu(index,false)">
								<li v-for="(child,index) in item.subMenu" v-if="!child.hidden" :key="'/'+child.menuRout" class="el-menu-item" style="padding-left: 40px;" :class="[$route.path==child.menuRout?'is-active':'',{'is-active':isActive[index]}]" @click="openSub(index,'/'+child.menuRout)">{{child.menuName}}</li>
							</ul>
						</template>
						<template v-else>
							<span class="el-submenu">
								<div class="el-submenu__title el-menu-item" style="padding-left: 20px;height: 56px;line-height: 56px;padding: 0 20px;" :class="$route.path==item.children[0].path?'is-active':''" @click="$router.push(item.children[0].path)"><i :class="item.iconCls"></i></div>
							</span>
						</template>
					</li>
				</ul>
			</aside>

			<section class="content-container">
				<div class="grid-content bg-purple-light">
					<el-col :span="24" class="breadcrumb-container">
						<strong class="title" style="min-width: 100%;">
							<!--{{$route.name}}-->
							<el-breadcrumb separator="/" class="breadcrumb-inner" style="float: left">
								<el-breadcrumb-item v-for="(item,index) in $route.matched" :key="item.path">
									<router-link :to="item.path" style="text-decoration: none;" v-if="index != $route.matched.length-1">{{ item.name }}</router-link>
									<span v-if="index == $route.matched.length-1">{{ item.name }}</span>
								</el-breadcrumb-item>
							</el-breadcrumb>
						</strong>
					</el-col>
					<el-col :span="24" class="content-wrapper">
						<transition name="fade" mode="out-in">
							<router-view></router-view>
						</transition>
					</el-col>
				</div>
			</section>
		</el-col>
	</el-row>
</template>

<script>
    import {homeService} from './HomeService'
    import util from '../../common/js/util'
    export default {
        data() {
            return {
                isActive:{},
                sysName:'芊芊學堂管理系統',
                collapsed:false,
                sysUserName: '',
                sysUserAvatar: '',
                menuList: [],
                defaultActive: '',
                envDefault:true,
                systemTopTxt:'',
                form: {
                    name: '',
                    region: '',
                    date1: '',
                    date2: '',
                    delivery: false,
                    type: [],
                    resource: '',
                    desc: ''
                }
            }
        },
        methods: {
            // 解决有三级路由时，只显示二级路由菜单的问题
            openSub(index,child) {
                this.isActive = {};
                this.$set(this.isActive,index,true);
                if (child.path) {
                    this.$router.push(child.path);
                } else {
                    this.$router.push(child);
                }
            },
            onSubmit() {
                //console.log('submit!');
            },
            handleopen(index,indexPath) {
                //console.log(index,indexPath)
            },
            handleclose() {
                //console.log('handleclose');
            },
            handleselect(a, b) {
            },
            //退出登录
            logout: function () {
                var _this = this;
                this.$confirm('确认退出吗?', '提示', {
                    //type: 'warning'
                }).then(() => {
                    sessionStorage.removeItem('user');
                    this.$cookieStore.delCookie('user');
                    _this.$router.push('/login');
                }).catch(() => {

                });
            },
            //折叠导航栏
            collapse:function(){
                this.collapsed=!this.collapsed;
            },
            showMenu(i,status){
                this.$refs.menuCollapsed.getElementsByClassName('submenu-hook-'+i)[0].style.display=status?'block':'none';
            },
            init() {
              let user = sessionStorage.getItem('user');

              let iconClsObj = {};
              this.$router.options.routes.forEach((routes,index) =>{
                if (routes.iconCls) {
                  // 一级路由对应的图标
                  // [routes.children[0]['path'].replace(/^\//,'')]
                  Object.assign(iconClsObj,{[routes.name]:routes.iconCls})
                }
              });
              this.envDefault = homeService.envDefault;
              if (user){
                user = JSON.parse(user);
                this.sysUserName = user.username || '';
              }
              if (user && !this.envDefault) {
                homeService.getMeunList(user.token).then(res =>{
                  let {subMenu,activeMenu} = res;
                  if (subMenu) {
                    subMenu.forEach(val => {
                      let  menuName = val.menuName.replace(/^[0-9]{1,2}\_/,'');
                      let subMenu = [];
                      val.subMenu.forEach(val_ => {
                        subMenu.push({menuRout:val_.menuRout,menuName:val_.menuName.replace(/^[0-9]{1,2}\_/,'')});
                      });
                      this.menuList.push({menuName:menuName,iconCls:iconClsObj[menuName],subMenu:subMenu});
                    });
                  }
                  if (this.menuList && this.menuList.length > 0  && this.menuList[0].subMenu) {
                    if (this.$route.path === '/') {
                      this.defaultActive = activeMenu ? '/'+activeMenu : '/'+res.subMenu[0].subMenu[0].menuRout;
                    } else {
                      let path = this.$route.path;
                      let pathArr = path.match(/\/\w{0,100}/g);
                      this.defaultActive = pathArr[0];
                    }
                  } else {
                    this.$router.push({path:'/login'});
                    //window.location.href = '/accounts/index.html';
                  }
                  this.$router.push({path:this.defaultActive,query:this.$route.query});
                });
              }
            },
        },
        mounted() {
            this.$nextTick(
            );
        },
		created() {
			this.init()
		},
        watch:{
            defaultActive:function (curVal,oldVal) {
                this.defaultActive = curVal;
            }
        }
    }

</script>


<style scoped lang="scss">
	@import '~scss_vars';
	.menu-color {
		background-color: #eef1f6 !important;
	}
	.container {
		position: absolute;
		top: 0px;
		bottom: 0px;
		width: 100%;
	.header {
		height: 60px;
		line-height: 60px;
		background: #3db6ee;
    background-image: url('../../assets/mohubg.jpg');
    width: 100%;
    background-position: center;
    background-repeat: no-repeat;
    background-size: 100%;
		color:#fff;
    opacity: 0.9;
	.userinfo {
		text-align: right;
		padding-right: 35px;
		float: right;
	.userinfo-inner {
		cursor: pointer;
		color:#fff;
	img {
		width: 40px;
		height: 40px;
		border-radius: 20px;
		margin: 10px 0px 10px 10px;
		float: right;
	}
	}
	}
	.logo {
	//width:230px;
		height:60px;
		font-size: 22px;
		padding-left:20px;
		padding-right:20px;
		border-color: rgba(238,241,146,0.3);
		border-right-width: 1px;
		border-right-style: solid;
	img {
		width: 40px;
		float: left;
		margin: 10px 10px 10px 18px;
		url:url(../../assets/logo.png);
	}
	.txt {
		color:#fff;
	}
	}
	.logo-width{
		width:230px;
	}
	.logo-collapse-width{
		width:60px
	}
	.tools{
		padding: 0px 23px;
		width:14px;
		height: 60px;
		line-height: 60px;
		cursor: pointer;
	}
	}
	.main {
		display: flex;
	// background: #324057;
		position: absolute;
		top: 60px;
		bottom: 0px;
		overflow: hidden;
	aside {
		flex:0 0 230px;
		width: 230px;
	// position: absolute;
	// top: 0px;
	// bottom: 0px;
	.el-menu{
		height: 100%;
	}
	.collapsed{
		width:60px;
	.item{
		position: relative;
	}
	.submenu{
		position:absolute;
		top:0px;
		left:60px;
		z-index:99999;
		height:auto;
		display:none;
	}

	}
	}
	.menu-collapsed{
		flex:0 0 60px;
		width: 60px;
	}
	.menu-expanded{
		flex:0 0 230px;
		width: 230px;
	}
	.content-container {
	// background: #f1f2f7;
		flex:1;
	// position: absolute;
	// right: 0px;
	// top: 0px;
	// bottom: 0px;
	// left: 230px;
		overflow-y: scroll;
		padding: 20px;
	.breadcrumb-container {
	//margin-bottom: 15px;
	.title {
		width: 200px;
		float: left;
		color: #475669;
	}
	.breadcrumb-inner {
		float: right;
	}
	}
	.content-wrapper {
		background-color: #fff;
		box-sizing: border-box;
	}
	}
	}
	}
    .systemTop{
        text-align: right;
        padding-right: 35px;
        /*float: right;*/
    }
</style>
