import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { ICalculated } from 'app/shared/model/calculated.model';
import { ICalculatedEntry } from 'app/shared/model/calculated-entry.model';
import { AccountService } from 'app/core';
import { CalculatedEntryService } from '../calculated-entry.service';
import { Router } from '@angular/router';
import { Chart } from 'chart.js';

@Component({
    selector: 'jhi-calculated-entry-findbyid',
    templateUrl: './calculated-entry-findbyid.component.html'
})
export class CalculatedEntryFindbyidComponent implements OnInit, OnDestroy {
    calculatedEntries: ICalculatedEntry[];
    calculated: ICalculated;
    currentAccount: any;
    eventSubscriber: Subscription;
    Entryid: any;
    protocolret: Protocolret[];
    chart = [];
    hasprotocol: boolean;
    totalcost: number;
    pro_tot_amount: number;
    pro_reagent_cost: number;
    pro_waste_cost: number;
    constructor(
        protected calculatedEntryService: CalculatedEntryService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService,
        protected router: Router
    ) {}

    loadAll() {
        this.calculatedEntryService.querybycalid(Number(this.Entryid)).subscribe(
            (res: HttpResponse<any>) => {
                this.calculatedEntries = res.body.protocolentry;
                this.protocolret = res.body.protocolret;

                this.pro_tot_amount = 0;
                this.pro_reagent_cost = 0;
                this.pro_waste_cost = 0;
                if (this.protocolret != null && this.protocolret.length > 0) {
                    for (let p of this.protocolret) {
                        this.pro_tot_amount += p.amount;
                        this.pro_reagent_cost += p.reagentcost;
                        this.pro_waste_cost += p.wastecost;
                    }
                }

                this.calculated = this.calculatedEntries[this.calculatedEntries.length - 1].calculated;
                this.hasprotocol = this.calculated.protocolname == null || this.calculated.protocolname.length == 0 ? false : true;
                this.totalcost = +Number(this.calculated.costresin + this.calculated.costaa + this.calculated.costwaste).toFixed(2);
                const seq = [];
                const difficulties = [];
                this.calculatedEntries
                    .slice()
                    .reverse()
                    .forEach(function(tmp) {
                        seq.push(tmp.sequencenumber + ' ' + tmp.aa);
                        difficulties.push(tmp.difficulty);
                    });
                this.chart = new Chart('canvas', {
                    type: 'bar',
                    data: {
                        labels: seq,
                        datasets: [
                            {
                                data: difficulties,
                                borderColor: '#3cba9f',
                                fill: false
                            }
                        ]
                    },
                    options: {
                        legend: {
                            display: false
                        },
                        scales: {
                            xAxes: [
                                {
                                    display: true
                                }
                            ],
                            yAxes: [
                                {
                                    display: true
                                }
                            ]
                        }
                    }
                });
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.Entryid = this.router.url.split('/').pop();

        this.loadAll();
        this.registerChangeInCalculatedEntries();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ICalculatedEntry) {
        return item.id;
    }

    registerChangeInCalculatedEntries() {
        this.eventSubscriber = this.eventManager.subscribe('calculatedEntryListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}

class Protocolret {
    solvent: string;
    amount: number;
    reagentcost: number;
    wastecost: number;
}
