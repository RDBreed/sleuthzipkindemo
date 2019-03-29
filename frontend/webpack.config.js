const HtmlWebpackPlugin = require('html-webpack-plugin');
const path = require('path');

module.exports = {
    mode: 'production',
    entry: ['./src/js/load.js'],
    output: {
        publicPath: '/',
        path: path.resolve(__dirname, 'dist'),
        filename: 'bundle.js',
        chunkFilename: 'bundle.js'
    },
    plugins: [
      new HtmlWebpackPlugin({
          template: './src/index.html',
          filename: 'index.html',
          inject: true,
          addDevelopmentFeatures:true,
      })
    ],
    devServer: {
        contentBase: [
            path.join(__dirname, "dist"),
            path.join(__dirname, 'src')
        ],
        compress: true,
        port: 8080
    }
};
