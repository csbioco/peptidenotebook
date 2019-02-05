import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProtocolentry } from 'app/shared/model/protocolentry.model';
import { ProtocolentryService } from './protocolentry.service';

@Component({
    selector: 'jhi-protocolentry-delete-dialog',
    templateUrl: './protocolentry-delete-dialog.component.html'
})
export class ProtocolentryDeleteDialogComponent {
    protocolentry: IProtocolentry;

    constructor(
        protected protocolentryService: ProtocolentryService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.protocolentryService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'protocolentryListModification',
                content: 'Deleted an protocolentry'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-protocolentry-delete-popup',
    template: ''
})
export class ProtocolentryDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ protocolentry }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ProtocolentryDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.protocolentry = protocolentry;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/protocolentry', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/protocolentry', { outlets: { popup: null } }]);
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
