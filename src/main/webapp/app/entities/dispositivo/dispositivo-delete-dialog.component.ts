import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDispositivo } from 'app/shared/model/dispositivo.model';
import { DispositivoService } from './dispositivo.service';

@Component({
    selector: 'jhi-dispositivo-delete-dialog',
    templateUrl: './dispositivo-delete-dialog.component.html'
})
export class DispositivoDeleteDialogComponent {
    dispositivo: IDispositivo;

    constructor(
        protected dispositivoService: DispositivoService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.dispositivoService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'dispositivoListModification',
                content: 'Deleted an dispositivo'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-dispositivo-delete-popup',
    template: ''
})
export class DispositivoDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ dispositivo }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(DispositivoDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.dispositivo = dispositivo;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/dispositivo', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/dispositivo', { outlets: { popup: null } }]);
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
