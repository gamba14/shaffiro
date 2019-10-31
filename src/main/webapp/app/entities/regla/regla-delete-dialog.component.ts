import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRegla } from 'app/shared/model/regla.model';
import { ReglaService } from './regla.service';

@Component({
    selector: 'jhi-regla-delete-dialog',
    templateUrl: './regla-delete-dialog.component.html'
})
export class ReglaDeleteDialogComponent {
    regla: IRegla;

    constructor(protected reglaService: ReglaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.reglaService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'reglaListModification',
                content: 'Deleted an regla'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-regla-delete-popup',
    template: ''
})
export class ReglaDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ regla }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ReglaDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.regla = regla;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/regla', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/regla', { outlets: { popup: null } }]);
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
