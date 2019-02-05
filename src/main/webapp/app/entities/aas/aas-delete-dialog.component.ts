import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAas } from 'app/shared/model/aas.model';
import { AasService } from './aas.service';

@Component({
    selector: 'jhi-aas-delete-dialog',
    templateUrl: './aas-delete-dialog.component.html'
})
export class AasDeleteDialogComponent {
    aas: IAas;

    constructor(protected aasService: AasService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.aasService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'aasListModification',
                content: 'Deleted an aas'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-aas-delete-popup',
    template: ''
})
export class AasDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ aas }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(AasDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.aas = aas;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/aas', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/aas', { outlets: { popup: null } }]);
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
