import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDispositivoNoAsociado } from 'app/shared/model/dispositivo-no-asociado.model';
import { DispositivoNoAsociadoService } from './dispositivo-no-asociado.service';

@Component({
    selector: 'jhi-dispositivo-no-asociado-delete-dialog',
    templateUrl: './dispositivo-no-asociado-delete-dialog.component.html'
})
export class DispositivoNoAsociadoDeleteDialogComponent {
    dispositivoNoAsociado: IDispositivoNoAsociado;

    constructor(
        protected dispositivoNoAsociadoService: DispositivoNoAsociadoService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.dispositivoNoAsociadoService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'dispositivoNoAsociadoListModification',
                content: 'Deleted an dispositivoNoAsociado'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-dispositivo-no-asociado-delete-popup',
    template: ''
})
export class DispositivoNoAsociadoDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ dispositivoNoAsociado }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(DispositivoNoAsociadoDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.dispositivoNoAsociado = dispositivoNoAsociado;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/dispositivo-no-asociado', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/dispositivo-no-asociado', { outlets: { popup: null } }]);
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
