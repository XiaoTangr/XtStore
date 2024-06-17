import axios from 'axios';




const InstanceUtil = axios.create({
    baseURL: 'http://localhost:8181/', // 设置你的API基础URL
    timeout: 5000, // 设置请求超时时间，单位毫秒
})

export default InstanceUtil;