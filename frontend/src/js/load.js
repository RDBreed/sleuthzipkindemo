import {Bread} from './toast-bread';
import {Egg} from './fry-egg';
import {Bacon} from './fry-bacon';
import {Coffee} from './coffee';
import {PropertyState} from './state';

(function () {
    document.addEventListener('DOMContentLoaded', function () {
        let State = new PropertyState();
        document.getElementById('toast-button').addEventListener('click', () => new Bread().getToastedBread(State));
        document.getElementById('egg').addEventListener('click', () => new Egg().getFriedEgg(State));
        document.getElementById('bacon').addEventListener('click', () => new Bacon().getFriedBacon(State));
        document.getElementById('start-coffee').addEventListener('click', () => new Coffee().getCoffee(State))
    });
})(this);
