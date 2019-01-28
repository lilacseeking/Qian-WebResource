import Vue from 'vue'
import IconSvg from '../../common/components/icon-svg/index.vue'// svg组件
// register globally
Vue.component('icon-svg', IconSvg);

// require multiple modules using Webpack's context API
const requireAll = requireContext => requireContext.keys().map(requireContext);
const req = require.context('./svg', false, /\\.svg$/);
requireAll(req);