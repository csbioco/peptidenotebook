import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICalculated } from 'app/shared/model/calculated.model';
import { CalculatedService } from './calculated.service';

@Component({
    selector: 'jhi-calculated-delete-dialog',
    templateUrl: './calculated-delete-dialog.component.html'
})
export class CalculatedDeleteDialogComponent {
    calculated: ICalculated;

    constructor(
        protected calculatedService: CalculatedService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.calculatedService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'calculatedListModification',
                content: 'Deleted an calculated'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-calculated-delete-popup',
    template: ''
})
export class CalculatedDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ calculated }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CalculatedDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.calculated = calculated;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/calculated', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/calculated', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
