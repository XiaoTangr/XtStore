import axios from 'axios';
import { ElMessage } from 'element-plus';
import JwtUtil from './JwtUtil';


const ApiUtil = axios.create({
    baseURL: 'http://localhost:8181/',
    timeout: 5000,
    headers: {
        "Content-Type": "multipart/form-data"
    }
});

// 设置请求头
ApiUtil.interceptors.request.use(
    (config) => {
        if (JwtUtil.isJwtExist()) {
            config.headers.Authorization = JwtUtil.getJwt();
        }
        return config;
    },
    (error) => {
        console.error('Request Interceptor Error:', error);
        return Promise.reject(error);
    }
);


// 处理响应头
ApiUtil.interceptors.response.use(
    (response) => {
        if (response.data.code === 200) {
            const authorizationHeader = response.headers['authorization']; // 使用小写 'authorization'
            if (authorizationHeader) {
                JwtUtil.setJwt(authorizationHeader);
            }
        } else if (response.data.code === 500) {
            ElMessage.error(response.data.msg);
        }

        return response;
    },
    (error) => {
        // Do something with response error
        console.error('Response Interceptor Error:', error);
        return Promise.reject(error);
    }
);

export default ApiUtil;
