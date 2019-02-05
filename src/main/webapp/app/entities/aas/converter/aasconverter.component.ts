import { Component } from '@angular/core';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

@Component({
    selector: 'jhi-aas-conver',
    templateUrl: './aasconverter.component.html'
})
export class AasconverterComponent {
    aaname: string = '';
    res: string = '';
    constructor(protected jhiAlertService: JhiAlertService, protected eventManager: JhiEventManager) {}

    convert() {
        console.log(this.res);
        if (this.aaname.includes('-')) {
            //threeleeter to oneletter

            this.res = '';
            const strs = this.aaname.split('-');
            for (var i = 0; i < strs.length; i++) {
                if (strs[i] === 'Ala') {
                    this.res = this.res.concat('A');
                } else if (strs[i] === 'Arg') {
                    this.res = this.res.concat('R');
                } else if (strs[i] === 'Asn') {
                    this.res = this.res.concat('N');
                } else if (strs[i] === 'Asp') {
                    this.res = this.res.concat('D');
                } else if (strs[i] === 'Cys') {
                    this.res = this.res.concat('C');
                } else if (strs[i] === 'Gln') {
                    this.res = this.res.concat('Q');
                } else if (strs[i] === 'Glu') {
                    this.res = this.res.concat('E');
                } else if (strs[i] === 'Gly') {
                    this.res = this.res.concat('G');
                } else if (strs[i] === 'His') {
                    this.res = this.res.concat('H');
                } else if (strs[i] === 'Ile') {
                    this.res = this.res.concat('I');
                } else if (strs[i] === 'Leu') {
                    this.res = this.res.concat('L');
                } else if (strs[i] === 'Lys') {
                    this.res = this.res.concat('K');
                } else if (strs[i] === 'Met') {
                    this.res = this.res.concat('M');
                } else if (strs[i] === 'Phe') {
                    this.res = this.res.concat('F');
                } else if (strs[i] === 'Pro') {
                    this.res = this.res.concat('P');
                } else if (strs[i] === 'Ser') {
                    this.res = this.res.concat('S');
                } else if (strs[i] === 'Thr') {
                    this.res = this.res.concat('T');
                } else if (strs[i] === 'Trp') {
                    this.res = this.res.concat('W');
                } else if (strs[i] === 'Tyr') {
                    this.res = this.res.concat('Y');
                } else if (strs[i] === 'Val') {
                    this.res = this.res.concat('V');
                } else {
                    this.res = 'The input seuquence is wrong!';
                    break;
                }
            }
        } else {
            //one letter to three letter

            this.res = '';
            for (var i = 0; i < this.aaname.length; i++) {
                if (this.aaname.charAt(i) === 'A') {
                    this.res = this.res.concat('-Ala');
                } else if (this.aaname.charAt(i) === 'R') {
                    this.res = this.res.concat('-Arg');
                } else if (this.aaname.charAt(i) === 'N') {
                    this.res = this.res.concat('-Asn');
                } else if (this.aaname.charAt(i) === 'D') {
                    this.res = this.res.concat('-Asp');
                } else if (this.aaname.charAt(i) === 'C') {
                    this.res = this.res.concat('-Cys');
                } else if (this.aaname.charAt(i) === 'Q') {
                    this.res = this.res.concat('-Gln');
                } else if (this.aaname.charAt(i) === 'E') {
                    this.res = this.res.concat('-Glu');
                } else if (this.aaname.charAt(i) === 'G') {
                    this.res = this.res.concat('-Gly');
                } else if (this.aaname.charAt(i) === 'H') {
                    this.res = this.res.concat('-His');
                } else if (this.aaname.charAt(i) === 'I') {
                    this.res = this.res.concat('-Ile');
                } else if (this.aaname.charAt(i) === 'L') {
                    this.res = this.res.concat('-Leu');
                } else if (this.aaname.charAt(i) === 'K') {
                    this.res = this.res.concat('-Lys');
                } else if (this.aaname.charAt(i) === 'M') {
                    this.res = this.res.concat('-Met');
                } else if (this.aaname.charAt(i) === 'F') {
                    this.res = this.res.concat('-Phe');
                } else if (this.aaname.charAt(i) === 'P') {
                    this.res = this.res.concat('-Pro');
                } else if (this.aaname.charAt(i) === 'S') {
                    this.res = this.res.concat('-Ser');
                } else if (this.aaname.charAt(i) === 'T') {
                    this.res = this.res.concat('-Thr');
                } else if (this.aaname.charAt(i) === 'W') {
                    this.res = this.res.concat('-Trp');
                } else if (this.aaname.charAt(i) === 'Y') {
                    this.res = this.res.concat('-Tyr');
                } else if (this.aaname.charAt(i) === 'V') {
                    this.res = this.res.concat('-Val');
                } else {
                    this.res = 'The input seuquence is wrong!';
                    break;
                }
            }
            this.res = this.res.substring(1);
        }
    }
}
