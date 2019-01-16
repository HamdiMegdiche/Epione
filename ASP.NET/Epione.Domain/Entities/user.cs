namespace Epione.Domain
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Data.Entity.Spatial;

    [Table("epione.user")]
    public partial class user
    {
        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2214:DoNotCallOverridableMethodsInConstructors")]
        public user()
        {
            appointments = new HashSet<appointment>();
            appointments1 = new HashSet<appointment>();
            complaints = new HashSet<complaint>();
            disscussions = new HashSet<discussion>();
            disscussions1 = new HashSet<discussion>();
            evaluations = new HashSet<evaluation>();
            holidays = new HashSet<holiday>();
            medicalrecords = new HashSet<medicalrecord>();
            schedules = new HashSet<schedule>();
            treatements = new HashSet<treatement>();
            motives = new HashSet<motive>();
            recommendations = new HashSet<recommendation>();
            medicalrecords1 = new HashSet<medicalrecord>();
        }

        [Required]
        [StringLength(31)]
        public string user_type { get; set; }

        public int id { get; set; }

        [StringLength(255)]
        public string firstName { get; set; }
        [StringLength(255)]
        public string address { get; set; }

        [StringLength(255)]
        public string lastName { get; set; }
        [StringLength(255)]
        public string sexe { get; set; }

        [StringLength(255)]
        public string civility { get; set; }

        public DateTime? creationDate { get; set; }

        [StringLength(255)]
        public string email { get; set; }

        public DateTime? lastLogin { get; set; }

        [StringLength(255)]
        public string password { get; set; }

        [StringLength(255)]
        public string phoneNumber { get; set; }

        [StringLength(255)]
        public string photo { get; set; }

        [StringLength(30)]
        public string role { get; set; }

        [StringLength(30)]
        public string status { get; set; }

        [StringLength(255)]
        public string token { get; set; }

        [StringLength(255)]
        public string username { get; set; }

        public double? latitude { get; set; }

        [StringLength(255)]
        public string licenseNumber { get; set; }

        public double? longitude { get; set; }

        [StringLength(255)]
        public string medicalfile { get; set; }

        [StringLength(255)]
        public string socialSecurityNumber { get; set; }
        [StringLength(255)]
        public string description { get; set; }

        public int? specialty_id { get; set; }

        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2227:CollectionPropertiesShouldBeReadOnly")]
        public virtual ICollection<appointment> appointments { get; set; }

        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2227:CollectionPropertiesShouldBeReadOnly")]
        public virtual ICollection<appointment> appointments1 { get; set; }

        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2227:CollectionPropertiesShouldBeReadOnly")]
        public virtual ICollection<complaint> complaints { get; set; }

        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2227:CollectionPropertiesShouldBeReadOnly")]
        public virtual ICollection<discussion> disscussions { get; set; }

        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2227:CollectionPropertiesShouldBeReadOnly")]
        public virtual ICollection<discussion> disscussions1 { get; set; }

        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2227:CollectionPropertiesShouldBeReadOnly")]
        public virtual ICollection<evaluation> evaluations { get; set; }

        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2227:CollectionPropertiesShouldBeReadOnly")]
        public virtual ICollection<holiday> holidays { get; set; }

        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2227:CollectionPropertiesShouldBeReadOnly")]
        public virtual ICollection<medicalrecord> medicalrecords { get; set; }

        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2227:CollectionPropertiesShouldBeReadOnly")]
        public virtual ICollection<schedule> schedules { get; set; }

        public virtual specialty specialty { get; set; }

        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2227:CollectionPropertiesShouldBeReadOnly")]
        public virtual ICollection<treatement> treatements { get; set; }

        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2227:CollectionPropertiesShouldBeReadOnly")]
        public virtual ICollection<motive> motives { get; set; }

        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2227:CollectionPropertiesShouldBeReadOnly")]
        public virtual ICollection<recommendation> recommendations { get; set; }

        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2227:CollectionPropertiesShouldBeReadOnly")]
        public virtual ICollection<medicalrecord> medicalrecords1 { get; set; }
    }
}
