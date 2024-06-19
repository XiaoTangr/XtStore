class JwtUtil {
    static setJwt = (jwt: string) => {
        // 保存JWT的逻辑
        localStorage.setItem('jwt', jwt);
    }

    static isJwtExist = () => {
        // 检查JWT是否存在的逻辑
        return localStorage.getItem('jwt') !== null && localStorage.getItem('jwt') !== undefined && localStorage.getItem('jwt') !== '';
    }

    static getJwt = () => {
        // 获取JWT的逻辑
        return localStorage.getItem('jwt') || null;
    }

    static deleteJwt = () => {
        // 删除JWT的逻辑
        localStorage.removeItem('jwt');
    }
}
export default JwtUtil;