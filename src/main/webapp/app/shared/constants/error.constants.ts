/**
 * 通过判断权限列表，决定页面某个标签是否显示。通过注入principal进行判断。其判断有点类似ngif的指令，根据输入的值，调用viewContainerRef重新绘制页面。
 * */
export const PROBLEM_BASE_URL = 'https://www.jhipster.tech/problem';
export const EMAIL_ALREADY_USED_TYPE = PROBLEM_BASE_URL + '/email-already-used';
export const LOGIN_ALREADY_USED_TYPE = PROBLEM_BASE_URL + '/login-already-used';
export const EMAIL_NOT_FOUND_TYPE = PROBLEM_BASE_URL + '/email-not-found';
