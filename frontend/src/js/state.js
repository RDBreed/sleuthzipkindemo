import {Http} from './http';

export function PropertyState() {
    let conversationId = null;
    let baseUrl = 'http://localhost:8081';

    function setConversationId(convId) {
        conversationId = convId;
    }

    function getConversationId() {
        return conversationId;
    }

    function getBaseUrl() {
        return baseUrl;
    }

    function callHealthEndpoint() {
        new Http().makeRequest('GET',
            baseUrl + '/breakfast/health', null)
            .then(
                function () {
                    document.getElementById('health-checker').style.backgroundColor = 'green';
                    setTimeout(callHealthEndpoint, 10000);
                }, function () {
                    document.getElementById('health-checker').style.backgroundColor = 'red';
                    setTimeout(callHealthEndpoint, 2000);
                });
    }

    this.setConversationId = setConversationId;
    this.getConversationId = getConversationId;
    this.getBaseUrl = getBaseUrl;
    this.callHealthEndpoint = callHealthEndpoint;

}
