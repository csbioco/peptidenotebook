import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IReagent } from 'app/shared/model/reagent.model';
import { ReagentService } from './reagent.service';

@Component({
    selector: 'jhi-reagent-delete-dialog',
    templateUrl: './reagent-delete-dialog.component.html'
})
export class ReagentDeleteDialogComponent {
    reagent: IReagent;

    constructor(protected reagentService: ReagentService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.reagentService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'reagentListModification',
                content: 'Deleted an reagent'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-reagent-delete-popup',
    template: ''
})
export class ReagentDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ reagent }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ReagentDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.reagent = reagent;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/reagent', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/reagent', { outlets: { popup: null } }]);
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
