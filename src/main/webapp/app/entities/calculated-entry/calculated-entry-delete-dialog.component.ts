import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICalculatedEntry } from 'app/shared/model/calculated-entry.model';
import { CalculatedEntryService } from './calculated-entry.service';

@Component({
    selector: 'jhi-calculated-entry-delete-dialog',
    templateUrl: './calculated-entry-delete-dialog.component.html'
})
export class CalculatedEntryDeleteDialogComponent {
    calculatedEntry: ICalculatedEntry;

    constructor(
        protected calculatedEntryService: CalculatedEntryService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.calculatedEntryService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'calculatedEntryListModification',
                content: 'Deleted an calculatedEntry'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-calculated-entry-delete-popup',
    template: ''
})
export class CalculatedEntryDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ calculatedEntry }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CalculatedEntryDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.calculatedEntry = calculatedEntry;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/calculated-entry', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/calculated-entry', { outlets: { popup: null } }]);
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
