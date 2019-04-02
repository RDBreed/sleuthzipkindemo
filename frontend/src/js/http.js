export function Http() {
    /**
     * Helper for http calls
     * @param method
     * @param url
     * @param conversationId
     * @param data
     * @returns {Promise}
     */
    function makeRequest(method, url, conversationId, data) {
        const theData = data || '';
        // Return a new promise.
        return new Promise(function (resolve, reject) {
            var req = new XMLHttpRequest();
            req.responseType = 'json';
            req.open(method, url);
            req.setRequestHeader('Access-Control-Allow-Origin', 'true');
            if (conversationId != null) {
                req.setRequestHeader('x-b3-convid', conversationId);
            }
            req.onload = function () {
                if (req.status === 200) {
                    resolve(req);
                }
                else {
                    reject(Error(req.statusText));
                }
            };
            req.onerror = function () {
                reject(Error('Something went wrong ... '));
            };
            req.send(theData);
        });
    }

    this.makeRequest = makeRequest;
}