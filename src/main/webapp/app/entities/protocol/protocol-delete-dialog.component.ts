import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProtocol } from 'app/shared/model/protocol.model';
import { ProtocolService } from './protocol.service';

@Component({
    selector: 'jhi-protocol-delete-dialog',
    templateUrl: './protocol-delete-dialog.component.html'
})
export class ProtocolDeleteDialogComponent {
    protocol: IProtocol;

    constructor(protected protocolService: ProtocolService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.protocolService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'protocolListModification',
                content: 'Deleted an protocol'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-protocol-delete-popup',
    template: ''
})
export class ProtocolDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ protocol }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ProtocolDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.protocol = protocol;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/protocol', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/protocol', { outlets: { popup: null } }]);
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
