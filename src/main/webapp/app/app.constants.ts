// These constants are injected via webpack environment variables.
// You can add more variables in webpack.common.ts or in profile specific webpack.<dev|prod>.js files.
// If you change the values in the webpack config files, you need to re run webpack to update the application

/**
 * 这里定义了一些全局变量，程序中会用到，实际上在运行webpack时候，会根据具体设定，替换这里的默认值（也就是说这里并不需要改）、
 参见webpack.common.js的StringReplacePlugin
 * */
export const VERSION = process.env.VERSION;
export const DEBUG_INFO_ENABLED: boolean = !!process.env.DEBUG_INFO_ENABLED;
export const SERVER_API_URL = process.env.SERVER_API_URL;
export const BUILD_TIMESTAMP = process.env.BUILD_TIMESTAMP;
